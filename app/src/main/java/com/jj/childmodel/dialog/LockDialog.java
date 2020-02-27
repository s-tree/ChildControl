package com.jj.childmodel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;

public class LockDialog extends Dialog {
    public LockDialog(@NonNull Context context) {
        super(context);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
