package peru.metro.metro;


import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.textfield.TextInputEditText;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import dmax.dialog.SpotsDialog;
import model.User;


public class registrarUsuarioActivity extends AppCompatActivity {
    private EditText nameUser, lastName,  gmail, password;
    private Button register;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        setTitle("Registrate");

        // casteando los texinput
        nameUser = findViewById(R.id.nombre);
        lastName = findViewById(R.id.apellido);
        gmail = findViewById(R.id.gmail);
        password = findViewById(R.id.password);


        register = findViewById(R.id.registrar);
        // instanciar
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dialog = new SpotsDialog.Builder().setContext(registrarUsuarioActivity.this).setMessage("Por favor espere..").build();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }


    void registerUser() {
        final String nUser = nameUser.getText().toString().trim();
        final String lName = lastName.getText().toString().trim();
        final String gGmail = gmail.getText().toString().trim();
        final String ppassword = password.getText().toString().trim();

        if(!nUser.isEmpty() && !lName.isEmpty()  && !gGmail.isEmpty() && !ppassword.isEmpty())
        {
            if (ppassword.length() >=6)
            {
                auth.createUserWithEmailAndPassword(gGmail, ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            saveUser(nUser, lName,  gGmail);
                        }else
                        {
                            Toast.makeText(registrarUsuarioActivity.this, "Problemas al registrar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else {

                Toast.makeText(registrarUsuarioActivity.this, "Contraseña mayor a 6 caracteres", Toast.LENGTH_LONG).show();
            }
        }else
        {


            Toast.makeText(registrarUsuarioActivity.this, "Rellena los campos", Toast.LENGTH_LONG).show();

        }
    }

    void saveUser(String name, String nameLast, String gmail )
    {
        User user = new User();
        user.setName(name);
        user.setLastName(nameLast);

        user.setGmail(gmail);
        dialog.show();
        databaseReference.child("Users").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(registrarUsuarioActivity.this, "Registro correcto",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(registrarUsuarioActivity.this, Login.class);
                    startActivity(i);
                    finish();

                }else {

                    Toast.makeText(registrarUsuarioActivity.this, "Error al registrar",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }
        });

        clear();
    }

    void clear()
    {
        nameUser.setText("");
        lastName.setText("");
        gmail.setText("");
        password.setText("");
    }

}

