# RightSelect

![device-2018-12-21-111438.png](https://upload-images.jianshu.io/upload_images/1760078-3f502afdc6ba288d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面经常有这样的列表样式选择的交互，一般通过startActivityForResult启动一个Activity，然后在onActivityResult中处理选择的返回。这样处理比较麻烦，而且涉及到intent传递数据类型的限制。

可用用DrawerLayout+ViewModel来简化这个选择交互的处理

使用方式


1. 启动选择页

```
  public void btnClick(View view) {
    RightSelectActivity.jumpWithFragmentContent(this,BlankFragment.class,null);
  }
```
其中BlankFragment是内容页（如上图 含有 sex age Button控件的页面）

响应选择事件
```
  public void btnAge() {
    final ArrayList<Integer> ages = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ages.add(18 + i);
    }

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
```

这样就很优雅的将选择参数的注入和 选择返回后数据的处理放在了一起，更好的阅读和维护代码

## 待优化
1. 目前的实现是直接将内容页作为Fragment传入，当内容页是ViewPager+Fragment实现时，就有点尴尬，不过任然可以将ViewPager+Fragment作为一个Fragment封装进来
2. 将RightSelectActivity 抽象化，将内容页的设置放开，这样可以很好的处理上面第一点说到的问题，然后也可以很方便的处理样式问题
3. 目前value和显示的summary都是以逗号分隔的字符串（后台提交数据的规则）还没有考虑其他数据格式的情况
4. 全屏页面选择，可以使用Navigation+Fragment+ViewModel来实现。
