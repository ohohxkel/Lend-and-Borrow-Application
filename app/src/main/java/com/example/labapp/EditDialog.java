package com.example.labapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditDialog extends AppCompatDialogFragment {

    private EditText editTextTransaction;
    private EditText editTextStudentNum;
    private EditDialogListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view).setTitle("Return Items").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String transaction = editTextTransaction.getText().toString();
                String studentNum = editTextStudentNum.getText().toString();
                listener.applyText(transaction, studentNum);

                //AdminScanner adminScanner = new AdminScanner();
                //adminScanner.returnItems();

            }
        });

        editTextTransaction = view.findViewById(R.id.edit_transaction);
        editTextStudentNum = view.findViewById(R.id.edit_studentNum);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement EditDialogListener");
        }
    }

    public interface EditDialogListener{
        void applyText(String transaction, String studentNum);
    }
}
