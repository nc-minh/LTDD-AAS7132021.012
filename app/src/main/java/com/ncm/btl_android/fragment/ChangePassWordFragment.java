package com.ncm.btl_android.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ncm.btl_android.R;

public class ChangePassWordFragment extends Fragment {

    private View mView;
    private EditText edtNewPassword;
    private Button btnChangePassword;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_change_pass_word,container, false);

        initUI();
        btnChangePassword.setOnClickListener(v -> {
            onClickChangePassword();
        });

        return mView;
    }

    private void initUI(){
        progressDialog = new ProgressDialog(getActivity());
        edtNewPassword = mView.findViewById(R.id.edt_new_password);
        btnChangePassword = mView.findViewById(R.id.btn_change_password);
    }

    private void onClickChangePassword(){
        String strNewPassword = edtNewPassword.getText().toString().trim();
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(strNewPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "User password updated", Toast.LENGTH_SHORT).show();
                        }else{
                            //hi???n th??? l??n nh???p l???i m???t kh???u v?? email ????? x??c nh???n ng?????i d??ng l???i khi ????ng nh???p qu?? l??u
                        }
                    }
                });
    }

    private void reAuthenticate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            onClickChangePassword();
                        }else{
                            Toast.makeText(getActivity(), "Vui l??ng nh???p l???i Email ho???c M???t kh???u!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
