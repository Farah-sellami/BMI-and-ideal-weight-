package com.imc;

import java.text.DecimalFormat;

public class Personne {
    public static final int HOMME = 0;
    public static final int FEMME = 1;
    int age ;
    float poids , taille ;

    public Personne(int age, float poids, float taille) {
        this.age = age;
        this.poids = poids;
        this.taille = taille;
    }

    public int getAge() {
        return age;
    }

    public float getPoids() {
        return poids;
    }

    public float getTaille() {
        return taille;
    }

    public double getImc() {
        return poids / Math.pow(taille, 2);
    }

    public double getPoidsIdeal(boolean estFemme) {
        if (estFemme)
            return taille * 100 - 100 - (taille * 100 - 150) / 2.5;
        else
            return taille * 100 - 100 - (taille * 100 - 150) / 4;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00"); //2chiffres aprés le virgule
        return poids+"Kg,"+taille+"m,"+age+"ans,"+"imc:"+df.format(getImc());
    }


}
