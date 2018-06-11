package com.example.entomologia.appbancodedados;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbhelper;
    EditText editTextNome, editTextEndereco, editTextEmpresa;
    Button btnInserir, btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dbhelper = new DBHelper(this);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEndereco = (EditText)findViewById(R.id.editTextEndereco);
        editTextEmpresa = (EditText) findViewById(R.id.editTextEmpresa);

        btnInserir = (Button) findViewById(R.id.btnInserir);
        btnListar = (Button) findViewById(R.id.btnListar);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNome.getText().length() > 0 && editTextEndereco.getText().length() > 0 &&
                        editTextEmpresa.getText().length() > 0){
                    dbhelper.insert(editTextNome.getText().toString(), editTextEndereco.getText().toString(),
                            editTextEmpresa.getText().toString());
                    AlertDialog.Builder adb = new  AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Sucesso");
                    adb.setMessage("Cadastro Realizado!");
                    adb.show();

                    editTextNome.setText("");
                    editTextEndereco.setText("");
                    editTextEmpresa.setText("");
                }
                else {
                    AlertDialog.Builder adb = new  AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Erro");
                    adb.setMessage("Todos os campos devem ser preenchidos!");
                    adb.show();

                    editTextNome.setText("");
                    editTextEndereco.setText("");
                    editTextEmpresa.setText("");
                }
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pega todos os contatos e lista
                List<Contato> contatos = dbhelper.queryGetAll();
                if (contatos == null) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Mensagem");
                    adb.setMessage("Não há registros cadastrados!");
                    adb.show();

                    editTextNome.setText("");
                    editTextEndereco.setText("");
                    editTextEmpresa.setText("");
                    return;
                }

                for (int i = 0; i < contatos.size(); i++) {
                    Contato contato = (Contato) contatos.get(i);
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Registro " + i);
                    adb.setMessage("Nome: " + contato.getNome() + "\nEndereço: " + contato.getEndereco() +
                    "\nEmpresa: " + contato.getEmpresa());
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //aperta OK, fecha a janela e vai para o próximo
                            dialog.dismiss();
                        }
                    });

                    adb.show();
                }
            }
        });
    }
}
