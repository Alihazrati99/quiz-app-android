package com.quiz_app;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Dialog_Number extends DialogFragment {
    View view;
    ProgressDialog progressDialog;
    EditText tv_number_text;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.number_dialog, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        tv_number_text =view.findViewById(R.id.edt);
        view.findViewById(R.id.tv_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f= tv_number_text.getText().toString().trim();
                if (f.equals("")){
                    tv_number_text.setError("Enter Question");
                    tv_number_text.requestFocus();
                    return;
                }
                if (Integer.parseInt(f)>6){
                    tv_number_text.setError("Invalid Number");
                    Toast.makeText(getActivity(), "Enter Number Between 0 - 6", Toast.LENGTH_SHORT).show();
                    tv_number_text.requestFocus();
                    return;
                }
                getActivity().getSharedPreferences("Quiz", Context.MODE_PRIVATE).edit().putInt("Number",Integer.parseInt(f)).commit();
                Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
