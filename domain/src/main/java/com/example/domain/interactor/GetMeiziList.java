package com.example.domain.interactor;

import com.example.domain.Meizi;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.MeiziRepository;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class GetMeiziList extends MeiziCase<Meizi,Integer> {

    private final MeiziRepository meiziRepository;

    @Inject
    GetMeiziList(MeiziRepository meiziRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.meiziRepository = meiziRepository;
    }

    @Override
    Observable<List<Meizi>> buildUseCaseObservable(Integer page) {
        return this.meiziRepository.meizis(page);
    }
}
