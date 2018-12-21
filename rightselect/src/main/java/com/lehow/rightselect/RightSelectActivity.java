package com.lehow.rightselect;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

public class RightSelectActivity extends AppCompatActivity  {

  DrawerLayout mDrawerLayout;
  RecyclerView recyclerView;

  RightSelectViewModel rightSelectViewModel;
  MAdapter mAdapter;

  public static void jumpWithFragmentContent(Context context,Class<? extends Fragment> contentFragment,Bundle fragmentArgument) {
    Intent intent = new Intent(context, RightSelectActivity.class);
    intent.putExtra("contentFragment", contentFragment.getName());
    intent.putExtra("fragmentArgument", fragmentArgument);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select);

    String contentFragment = getIntent().getStringExtra("contentFragment");
    try {
      Class<Fragment> fragmentClass = (Class<Fragment>) Class.forName(contentFragment);
      Fragment fragment = fragmentClass.newInstance();
      fragment.setArguments(getIntent().getBundleExtra("fragmentArgument"));
      getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("请指定 contentFragment");
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

    rightSelectViewModel = ViewModelProviders.of(this).get(RightSelectViewModel.class);
    mDrawerLayout = findViewById(R.id.drawer_layout);
    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    mAdapter = new MAdapter();
    recyclerView.setAdapter(mAdapter);
    rightSelectViewModel.listenOpenSelectEvent().subscribe(new Consumer<OptionEntity>() {
      @Override public void accept(OptionEntity optionEntity) throws Exception {
        Log.i("TAG", "accept: openDrawer "+Thread.currentThread());
        mAdapter.updateOption(optionEntity);//先更新再打开，否则显示有问题
        mDrawerLayout.openDrawer(GravityCompat.END);
      }
    });

    mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
      @Override public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        Log.i("TAG", "onDrawerClosed: ");
        rightSelectViewModel.notifySelectedResult(mAdapter.optionEntity.getSelectedIndexs());
      }
    });
  }

  public void btnClick(View view) {
    mDrawerLayout.openDrawer(GravityCompat.END);

  }

  public void btnSex(View view) {
    ArrayList<String> sexs = new ArrayList<>();
    sexs.add("男");
    sexs.add("女");


    rightSelectViewModel.singleMustSelectFrom(sexs, "", new OptionConverter<String>() {
      @Override public String getTitle(String entity) {
        return entity;
      }

      @Override public String getValue(String entity) {
        return entity;
      }
    }).subscribe(new Consumer<SelectedResult>() {
      @Override public void accept(SelectedResult selectedResult) throws Exception {
        Log.i("TAG", "btnSex accept: getSummary="
            + selectedResult.getSummary()
            + " getValues="
            + selectedResult.getValues());
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {

      }
    }, new Action() {
      @Override public void run() throws Exception {
        Log.i("TAG", "run: ");
      }
    });
  }

  public void btnAge(View view) {
    final ArrayList<Integer> ages = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ages.add(18 + i);
    }
    final int[] selValue = { 0 };

    rightSelectViewModel.singleSelectFrom(ages, "18", new OptionConverter<Integer>() {
      @Override public String getTitle(Integer entity) {
        return entity.toString();
      }

      @Override public String getValue(Integer entity) {
        return entity.toString();
      }
    }).subscribe(new Consumer<SelectedResult>() {
      @Override public void accept(SelectedResult selectedResult) throws Exception {
        Log.i("TAG", "btnAge accept: getSummary="+selectedResult.getSummary()+" getValues="+selectedResult.getValues());
      }
    });
  }

  public void btnNull(View view) {
    final ArrayList<Integer> ages = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ages.add(18 + i);
    }
    rightSelectViewModel.multipleSelectFrom(ages, "18", new OptionConverter<Integer>() {
      @Override public String getTitle(Integer entity) {
        return entity.toString();
      }

      @Override public String getValue(Integer entity) {
        return entity.toString();
      }
    }).subscribe(new Consumer<SelectedResult>() {
      @Override public void accept(SelectedResult selectedResult) throws Exception {
        Log.i("TAG", "accept: getSummary="+selectedResult.getSummary()+" getValues="+selectedResult.getValues());
      }
    });
  }

  private class MAdapter extends RecyclerView.Adapter<MVH> {
    OptionEntity optionEntity=new OptionEntity();

    void updateOption(OptionEntity optionEntity) {
      this.optionEntity = optionEntity;
      notifyDataSetChanged();
    }
    @NonNull @Override public MVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

      return new MVH(getLayoutInflater().inflate(R.layout.layout_select_item,viewGroup,false));
    }

    @Override public void onBindViewHolder(@NonNull MVH mvh, final int i) {
      mvh.title.setText(optionEntity.getItem(i));
      mvh.title.setTextColor(optionEntity.isSelected(i)?Color.RED:Color.BLACK);
      mvh.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (optionEntity.select(i)) {
            mDrawerLayout.closeDrawers();
          }
        }
      });
    }

    @Override public int getItemCount() {
      return optionEntity.size();
    }
  }

  private class MVH extends RecyclerView.ViewHolder {
    TextView title;
    public MVH(@NonNull View itemView) {
      super(itemView);
      title = (TextView) itemView;
    }
  }
}
