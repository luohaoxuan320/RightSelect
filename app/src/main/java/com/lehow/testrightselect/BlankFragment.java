package com.lehow.testrightselect;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lehow.rightselect.OptionConverter;
import com.lehow.rightselect.RightSelectViewModel;
import com.lehow.rightselect.SelectedResult;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

  private RightSelectViewModel selectViewModel;
  public BlankFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    selectViewModel = ViewModelProviders.of(getActivity()).get(RightSelectViewModel.class);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_blank, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.findViewById(R.id.btnSex).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
          btnSex();
      }
    });

    view.findViewById(R.id.btnAge).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        btnAge();
      }
    });
  }


  public void btnSex() {
    ArrayList<String> sexs = new ArrayList<>();
    sexs.add("男");
    sexs.add("女");


    selectViewModel.singleMustSelectFrom(sexs, "", new OptionConverter<String>() {
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

  public void btnAge() {
    final ArrayList<Integer> ages = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ages.add(18 + i);
    }
    final int[] selValue = { 0 };

    selectViewModel.singleSelectFrom(ages, "18", new OptionConverter<Integer>() {
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
}
