package com.lehow.rightselect;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;

/**
 * desc:
 * author: luoh17
 * time: 2018/12/15 16:55
 */
public class RightSelectViewModel extends ViewModel {

  private PublishSubject<OptionEntity> openSelectSubject = PublishSubject.create();
  private PublishSubject<ArrayList<Integer>> selectedSubject = PublishSubject.create();
  public <T> Observable<SelectedResult> multipleSelectFrom(final ArrayList<T> dataSrc,String selectedValue,
      final OptionConverter<T> optionConverter) {
    openSelectSubject.onNext(new OptionEntity(dataSrc,selectedValue,OptionEntity.MULTI_CHOICE,optionConverter));
    return selectedSubject.take(1).flatMap(new Function<ArrayList<Integer>, Observable<SelectedResult>>() {
      @Override public Observable<SelectedResult> apply(ArrayList<Integer> integers) throws Exception {
        StringBuilder values = new StringBuilder();
        StringBuilder summary = new StringBuilder();
        for (Integer integer : integers) {
          T option = dataSrc.get(integer);
          values.append(optionConverter.getValue(option));
          values.append(",");
          summary.append(optionConverter.getTitle(option));
          summary.append(",");
        }
        if (values.length()>0) values.deleteCharAt(values.length() - 1);
        if (summary.length()>0) summary.deleteCharAt(summary.length() - 1);

        return Observable.just(new SelectedResult(values.toString(), summary.toString()));
      }
    });
  }

  public <T> Observable<SelectedResult> singleSelectFrom(final ArrayList<T> dataSrc,String selectedValue,
      final OptionConverter<T> optionConverter){

        openSelectSubject.onNext(new OptionEntity(dataSrc,selectedValue,OptionEntity.SINGLE_CHOICE,optionConverter));

    return selectedSubject.take(1).flatMap(new Function<ArrayList<Integer>, ObservableSource<SelectedResult>>() {
      @Override public ObservableSource<SelectedResult> apply(ArrayList<Integer> integers) throws Exception {
        if (!integers.isEmpty()) {
          return Observable.just(
              new SelectedResult(optionConverter.getValue(dataSrc.get(integers.get(0))),
                  optionConverter.getTitle(dataSrc.get(integers.get(0)))));
        }
        return Observable.just(new SelectedResult("", ""));
      }
    });

  }

  public <T> Observable<SelectedResult> singleMustSelectFrom(final ArrayList<T> dataSrc,String selectedValue,
      final OptionConverter<T> optionConverter){

    openSelectSubject.onNext(new OptionEntity(dataSrc,selectedValue,OptionEntity.SINGLE_CHOICE_MUST,optionConverter));
    return selectedSubject.take(1).flatMap(new Function<ArrayList<Integer>, Observable<SelectedResult>>() {
      @Override public Observable<SelectedResult> apply(ArrayList<Integer> integers) throws Exception {
        if (!integers.isEmpty()) {
          return Observable.just(
              new SelectedResult(optionConverter.getValue(dataSrc.get(integers.get(0))),
                  optionConverter.getTitle(dataSrc.get(integers.get(0)))));
        }
        return Observable.just(new SelectedResult("", ""));
      }
    });
  }



  public PublishSubject<OptionEntity> listenOpenSelectEvent() {
    return openSelectSubject;
  }

  public  void  notifySelectedResult(ArrayList<Integer> selectedIndexs){
    Log.i("TAG", "notifySelectedResult: ");
    selectedSubject.onNext(selectedIndexs);
  }



}
