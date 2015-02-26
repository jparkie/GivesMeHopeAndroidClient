/*
 * Copyright 2013 – Xavi Rigau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jparkie.givesmehope.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int resourceId;

    public DrawableMatcher(int resourceId) {
        super(View.class);
        this.resourceId = resourceId;
    }

    private String resourceName = null;
    private Drawable expectedDrawable = null;

    @Override
    public boolean matchesSafely(View target) {
        if (expectedDrawable == null) {
            loadDrawableFromResources(target.getResources());
        }
        if (invalidExpectedDrawable()) {
            return false;
        }

        if (target instanceof ImageView) {
            return hasImage((ImageView) target) || hasBackground(target);
        }
        if (target instanceof TextView) {
            return hasCompoundDrawable((TextView) target) || hasBackground(target);
        }
        return hasBackground(target);
    }

    private void loadDrawableFromResources(Resources resources) {
        try {
            expectedDrawable = resources.getDrawable(resourceId);
            resourceName = resources.getResourceEntryName(resourceId);
        } catch (Resources.NotFoundException ignored) {
            // view could be from a context unaware of the resource id.
        }
    }

    private boolean invalidExpectedDrawable() {
        return expectedDrawable == null;
    }

    private boolean hasImage(ImageView target) {
        return isSameDrawable(target.getDrawable());
    }

    private boolean hasCompoundDrawable(TextView target) {
        if (target.getCompoundDrawables() == null) {
            return false;
        }
        for (Drawable drawable : target.getCompoundDrawables()) {
            if (isSameDrawable(drawable)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBackground(View target) {
        return isSameDrawable(target.getBackground());
    }

    private boolean isSameDrawable(Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        return expectedDrawable.getConstantState().equals(drawable.getConstantState());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(resourceId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}