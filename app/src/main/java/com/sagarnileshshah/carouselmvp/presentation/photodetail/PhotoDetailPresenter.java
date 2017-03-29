package com.sagarnileshshah.carouselmvp.presentation.photodetail;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.mvp.BasePresenter;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.util.List;

/**
 * Created by sshah on 3/25/17.
 */

public class PhotoDetailPresenter extends BasePresenter<PhotoDetailContract.View> implements PhotoDetailContract.Presenter {

    private DataRepository dataRepository;
    private ThreadExecutor threadExecutor;
    private MainUiThread mainUiThread;

    public PhotoDetailPresenter(PhotoDetailContract.View view, DataRepository dataRepository,
                                ThreadExecutor threadExecutor, MainUiThread mainUiThread) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.threadExecutor = threadExecutor;
        this.mainUiThread = mainUiThread;
    }

    @Override
    public void getComments(Context context, Photo photo) {
        view.setProgressBar(true);

        dataRepository.getComments(context, photo, new DataSource.GetCommentsCallback() {
            @Override
            public void onSuccess(List<Comment> comments) {
                if (view != null) {
                    view.showComments(comments);
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage(view.getContext().getString(R.string.error_msg));
                }
            }

            @Override
            public void onNetworkFailure() {
                if (view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage(view.getContext().getString(R.string.network_failure_msg));
                }
            }
        });
    }
}