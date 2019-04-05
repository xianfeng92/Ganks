package com.example.domain.interactor;

import com.example.domain.Meizi;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.fernandocejas.arrow.checks.Preconditions;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public abstract class MeiziCase<T,Params> {

    private final CompositeDisposable disposables;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    MeiziCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link MeiziCase}.
     */
    abstract Observable<List<Meizi>> buildUseCaseObservable(Params params);

    abstract Observable<List<Meizi>> buildUseCaseObservable();

    public void addToFavorite(Meizi meizi){}

    /**
     * Executes the current use case.
     *  @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DefaultObserver observer, Integer params) {
        Preconditions.checkNotNull(observer);
        final Observable<List<Meizi>> observable = this.buildUseCaseObservable((Params) params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Executes the current use case.
     */
    public void execute(DefaultObserver observer) {
        Preconditions.checkNotNull(observer);
        final Observable<List<Meizi>> observable = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }

}
