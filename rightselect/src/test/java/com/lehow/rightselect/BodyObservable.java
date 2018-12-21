package com.lehow.rightselect;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * desc:
 * author: luoh17
 * time: 2018/12/17 18:53
 */
public class BodyObservable<T> extends Observable<T> {
  private final Observable<T> upstream;

  BodyObservable(Observable<T> upstream) {
    this.upstream = upstream;
  }

  @Override protected void subscribeActual(Observer<? super T> observer) {
    upstream.subscribe(new BodyObserver<T>(observer));
  }

  private static class BodyObserver<R> implements Observer<R> {
    private final Observer<? super R> observer;
    private boolean terminated;

    BodyObserver(Observer<? super R> observer) {
      this.observer = observer;
    }

    @Override public void onSubscribe(Disposable disposable) {
      observer.onSubscribe(disposable);
    }

    @Override public void onNext(R response) {
      System.out.println("onNext="+response);
        observer.onNext(null);
    }

    @Override public void onComplete() {
      if (!terminated) {
        observer.onComplete();
      }
    }

    @Override public void onError(Throwable throwable) {
      if (!terminated) {
        observer.onError(throwable);
      } else {
        // This should never happen! onNext handles and forwards errors automatically.
        Throwable broken = new AssertionError(
            "This should never happen! Report as a bug with the full stacktrace.");
        //noinspection UnnecessaryInitCause Two-arg AssertionError constructor is 1.7+ only.
        broken.initCause(throwable);
        RxJavaPlugins.onError(broken);
      }
    }
  }
}
