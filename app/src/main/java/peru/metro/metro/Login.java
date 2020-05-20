package peru.metro.metro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;


public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText gmail, password;
    private Button login, recover;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Agregando titulo centrado en el actionBar
        setTitle("Login");
        mAuth = FirebaseAuth.getInstance();

        gmail = findViewById(R.id.gmail);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        recover = findViewById(R.id.nuevaCuenta);
        dialog = new SpotsDialog.Builder().setContext(Login.this).setMessage("Por favor espere...").build();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
    }

    private void go() {
        Intent i  = new Intent(Login.this, registrarUsuarioActivity.class);
        startActivity(i);
    }

    void login() {

        // creamos dos variables que conviertan las entradas a un tostring
        //trim(() para eliminar espacios al inicio y al final
        String userE = gmail.getText().toString().trim();
        String passE = password.getText().toString().trim();
        // luego creamos una condicion diciendo si viene en blanco manda un mensaje que diga coloca algo
        if (TextUtils.isEmpty(userE)){
            Toast.makeText(Login.this, "Ingrese nombre de usuario", Toast.LENGTH_SHORT).show();
        }
        // las misma condicional para el campo passE
        else if (TextUtils.isEmpty(passE)){

            Toast.makeText(Login.this, "Ingrese la clave de usuario", Toast.LENGTH_SHORT).show();
        }else
        {
            // utilizamos la variable que declaramos al principio
            // le decimos que queremos loguearnos por correo y contrase;a
            dialog.show();
            mAuth.signInWithEmailAndPassword(userE,passE)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // si la tarea es diferente de exito pues mandar un mensaje que algo esta
                            // incorrecto

                            if (!task.isSuccessful()){
                                Toast.makeText(Login.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                            }else {
                                // si sale bien lanzarse de  una actividad hacia otra
                                Intent intent = new Intent(Login.this,cardview_menu.class);
                                startActivity(intent);
                                finish();
                            }
                            dialog.dismiss();
                        }
                    });
            clear();

        }
    }

    void clear()
    {
        gmail.setText("");
        password.setText("");
    }
}
