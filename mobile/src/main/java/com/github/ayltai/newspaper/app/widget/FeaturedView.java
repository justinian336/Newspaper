package com.github.ayltai.newspaper.app.widget;

import java.util.List;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.ayltai.newspaper.R;
import com.github.ayltai.newspaper.app.data.model.Image;
import com.github.ayltai.newspaper.media.FrescoImageLoader;
import com.github.ayltai.newspaper.util.Irrelevant;
import com.github.ayltai.newspaper.util.RxUtils;
import com.github.ayltai.newspaper.util.TestUtils;
import com.jakewharton.rxbinding2.view.RxView;

public final class FeaturedView extends ItemView {
    public static final int VIEW_TYPE = R.id.view_type_featured;

    //region Components

    private final KenBurnsView image;
    private final TextView     title;

    //endregion

    public FeaturedView(@NonNull final Context context) {
        super(context);

        final View view = LayoutInflater.from(context).inflate(R.layout.view_news_featured, this, true);

        this.container = view.findViewById(R.id.container);
        this.image     = view.findViewById(R.id.featured_image);
        this.title     = view.findViewById(R.id.title);
    }

    //region Properties

    @Override
    public void setTitle(@Nullable final CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            this.title.setVisibility(View.GONE);
        } else {
            this.title.setVisibility(View.VISIBLE);
            this.title.setText(title);
        }
    }

    @SuppressWarnings("IllegalCatch")
    @Override
    public void setImages(@NonNull final List<Image> images) {
        if (images.isEmpty()) {
            this.image.setVisibility(View.GONE);
        } else {
            if (TestUtils.isLoggable()) Log.d(this.getClass().getSimpleName(), "Featured image = " + images.get(0).getUrl());

            FrescoImageLoader.loadImage(images.get(0).getUrl())
                .compose(RxUtils.applyMaybeBackgroundToMainSchedulers())
                .subscribe(
                    bitmap -> {
                        this.image.setImageBitmap(bitmap);

                        if (TestUtils.isRunningInstrumentedTest()) this.image.pause();
                    },
                    error -> {
                        if (TestUtils.isLoggable()) Log.e(this.getClass().getSimpleName(), error.getMessage(), error);
                    }
                );

            this.image.setVisibility(View.VISIBLE);
        }
    }

    //endregion

    @CallSuper
    @Override
    protected void onAttachedToWindow() {
        this.manageDisposable(RxView.clicks(this.image).subscribe(irrelevant -> this.clicks.onNext(Irrelevant.INSTANCE)));

        super.onAttachedToWindow();
    }
}