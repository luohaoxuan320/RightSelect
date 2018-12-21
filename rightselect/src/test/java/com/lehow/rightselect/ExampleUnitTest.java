package com.lehow.rightselect;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() {



    /*Maybe.create(new MaybeOnSubscribe<Integer>() {
      @Override public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
        emitter.onSuccess(null);
        emitter.onComplete();
      }
    })*/

 /*   Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        emitter.onNext(100);
      }
    });
    new BodyObservable<>(integerObservable).subscribe(new Observer<Integer>() {
      @Override public void onSubscribe(Disposable d) {

      }

      @Override public void onNext(Integer integer) {
        System.out.println("onNext="+integer);
      }

      @Override public void onError(Throwable e) {
        System.out.println("onError="+e);
      }

      @Override public void onComplete() {

      }
    });*/

        /*subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {
        System.out.println("accept=" + integer);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        System.out.println("accept="+throwable);
      }
    }, new Action() {
      @Override public void run() throws Exception {
        System.out.println("run");
      }
    });*/

    final PublishSubject<Integer> integerPublishSubject = PublishSubject.create();
    integerPublishSubject.take(1).subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {
        System.out.println("accept=" + integer);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        System.out.println("accept=" + throwable);
      }
    }, new Action() {
      @Override public void run() throws Exception {
        System.out.println("run=" );

      }
    });
    integerPublishSubject.onNext(11);
    integerPublishSubject.subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {
        System.out.println("accept2=" + integer);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        System.out.println("accept2=" + throwable);
      }
    }, new Action() {
      @Override public void run() throws Exception {
        System.out.println("run2=" );

      }
    });
    integerPublishSubject.onNext(12);
    assertEquals(4, 2 + 2);

  }

  class BaseInfo{
    private int customerType;
    private String name;
    private String phone;
    private int sex;
    private int age;
    private int intention;
    private int konwFrom;
    private int comeFrom;
    private String address;
  }

}