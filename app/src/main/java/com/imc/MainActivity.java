package com.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText edPoids, edTaille, edAge;
    private Button btnAjouter, btnEffacer, btnVider;
    private ListView lstPersonne;
    private ArrayAdapter<Personne> adpP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        edPoids = findViewById(R.id.edPoids);
        edTaille = findViewById(R.id.edTaille);
        edAge = findViewById(R.id.edAge);
        btnAjouter = findViewById(R.id.btnA);
        btnEffacer = findViewById(R.id.btnE);
        btnVider = findViewById(R.id.btnV);
        lstPersonne = findViewById(R.id.lstP);
        adpP = new ArrayAdapter<Personne>(this, android.R.layout.simple_list_item_1);
        lstPersonne.setAdapter(adpP);
        ecouteurs();
    }

    private void ecouteurs() {
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouter();
            }
        });

        btnEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effacer();
            }
        });

        btnVider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vider();
            }
        });

        lstPersonne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                afficher(position);
            }
        });
    }

    private void afficher(int position) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Homme/Femme?");
        b.setMessage("vous etes ?");
        b.setPositiveButton("Femme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                afficheFH(position, true);
            }
        });
        b.setNegativeButton("Homme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                afficheFH(position, false);
            }
        });
        AlertDialog alertDialog = b.create();
        alertDialog.show();
    }

    private void afficheFH(int position, boolean gender) {
        Personne p = adpP.getItem(position);
        DecimalFormat df = new DecimalFormat("0.00");
        String message = "IMC: " + df.format(p.getImc()) + "\n";
        message += "Interprétation : ";
        if (p.getImc() < 18.5)
            message += "Maigreur \n";
        else if (p.getImc() < 30)
            message += "Corpulence normale \n";
        else
            message += "Surpoids \n";
        String type;
        if (gender)
            type = "F";
        else
            type = "H";
        message += "Poids Idéal (" + type + ") : "
                + p.getPoidsIdeal(gender);
        Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
        t.show();


    }


    private void vider() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Vider?");
        b.setMessage("etes vous sur de vider la liste ?");
        b.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                adpP.clear();
            }
        });
        b.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = b.create();
        alertDialog.show();
    }

    private void effacer() {
        edPoids.setText("");
        edTaille.setText("");
        edAge.setText("");
        edPoids.requestFocus();
    }

    private void ajouter() {
        if (!edPoids.getText().toString().isEmpty() &&
                !edTaille.getText().toString().isEmpty() &&
                !edAge.getText().toString().isEmpty()) {
            int age = Integer.parseInt(edAge.getText().toString());
            if (age > 18) {
                float poids = Float.parseFloat(edPoids.getText().toString());
                float taille = Float.parseFloat(edTaille.getText().toString());
                Personne p = new Personne(age, poids, taille);
                adpP.add(p);
                effacer();
            } else {
                Toast t = Toast.makeText(this,
                        "Ce calcul uniquement pour un adulte!",
                        Toast.LENGTH_LONG);
                t.show();
            }
        } else {
            Toast t = Toast.makeText(this, "Vérifier les valeurs SVP!", Toast.LENGTH_LONG);
            t.show();
        }
    }
}