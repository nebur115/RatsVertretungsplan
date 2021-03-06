package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class create_stundenplan_stunden_obtionen  extends AppCompatActivity {


    boolean zweiWöchentlich;
    private List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int pos;
    int Stunde;
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();
    int orgStunde;
    int orgPos;
    Boolean wasDopellstunde = false;
    Boolean wasWöchentlich = false;
    String kursname = "";
    Toast toast;
    String fach;
    String Kursart;
    int Kursnummer;
    String Unterichtbegin;
    String Bewertung;
    String Raum;
    String Lehrer;
    boolean Doppelstunde;
    String Wiederholung;
    String fachkürzel;
    int StartJahr;
    boolean Schriftlich;
    int MaxStunden;
    Spinner KursartenSpinner;
    Spinner WiederholungsSpinner;
    Spinner MündlichSchriftlichSpinner;
    TextView DatumStunde;
    Spinner sRaumSchule;
    Spinner sHalle;
    String Wochentag;
    ConstraintLayout cFachEingabe;
    ConstraintLayout cKursEingabe;
    ConstraintLayout cLehrerEingabe;
    ConstraintLayout cRaumEingabe;
    ConstraintLayout cUnterichtstartEingabe;
    ConstraintLayout cDoppelstundeCheck;
    ConstraintLayout cWiederholungAuswahl;
    ConstraintLayout cMündlichSchriftlichAuswahl;
    ConstraintLayout cHalleEingabe;
    String Stufe;
    List<String> kursarten;
    EditText eKursnummer;
    EditText eUnterichtbegin;
    EditText eRaum;
    CheckBox eDoppelstunde;
    EditText eLehrer;
    EditText eSchule;

    private static  final String[] Fächer = {
            "MathePhysikInformatik", "BioChemie", "Deutsch", "Englisch", "Französisch", "Latein", "Spanisch", "Italiensich", "Niederländisch", "Kunst", "Musik", "Literatur", "Geschichte", "Sozialwissenschaften", "Erdkunde", "Philosophie", "Pädagogik", "Religion (Ev.)","Religion (Kath.)", "Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Sport", "Politik"
    };

    int Woche;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);


        Intent intent = getIntent();
        Woche = intent.getExtras().getInt("Woche");
        pos = intent.getExtras().getInt("Position");
        orgPos = pos;
        zweiWöchentlich = intent.getExtras().getBoolean("ZweiWöchentlich");
        setContentView(R.layout.create_stundenplan_stunden_obtionen);


        super.onCreate(savedInstanceState);


        MaxStunden = settings.getInt("MaxStunden",0);
        KursartenSpinner = (Spinner) findViewById(R.id.kursartspinner);
        WiederholungsSpinner = (Spinner) findViewById(R.id.wiederholungspinner) ;
        MündlichSchriftlichSpinner = (Spinner) findViewById(R.id.MüdnlichSchriftlichspinner) ;
        DatumStunde = (TextView) findViewById(R.id.DatumStunde);
        sRaumSchule = (Spinner) findViewById(R.id.RaumSpinner);
        sHalle = (Spinner) findViewById(R.id.HalleSpinner);
        cFachEingabe = findViewById(R.id.FachEingabe);
        cKursEingabe = findViewById(R.id.KursEingabe);
        cLehrerEingabe = findViewById(R.id.LehrerEingabe);
        cRaumEingabe = findViewById(R.id.RaumEingabe);
        cUnterichtstartEingabe = findViewById(R.id.UnterichtstartEingabe);
        cDoppelstundeCheck = findViewById(R.id.DoppelstundeCheck);
        cWiederholungAuswahl = findViewById(R.id.WiederholungAuswahl);
        cMündlichSchriftlichAuswahl = findViewById(R.id.MündlicSchriflichtAuswahl);
        cHalleEingabe = findViewById(R.id.HalleEingabe);
        cUnterichtstartEingabe.setVisibility(View.GONE);
        Stufe = settings.getString("Stufe", "DEFAULT");
        kursarten = new ArrayList<String>();
        kursarten.add("Grundkurs");
        eKursnummer = findViewById(R.id.Kursnummer);
        eUnterichtbegin = findViewById(R.id.Unterichtbegin);
        eRaum = findViewById(R.id.Raum);
        eDoppelstunde = (CheckBox) findViewById(R.id.Doppelstunde);
        eLehrer = findViewById(R.id.Lehrer);
        eSchule = findViewById(R.id.Schule);
        boolean hinweis;
        String json;
        Gson gson = new Gson();
        if(Woche==1){
            json = settings.getString("Stundenliste", null);
        }
        else{
            json = settings.getString("WocheBStundenListe", null);
        }



        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
        MemoryStundenListe = gson.fromJson(json , type);


        if(zweiWöchentlich){
            String bjson;
            if(!(Woche==1)){
                bjson = settings.getString("Stundenliste", null);
            }
            else{
                bjson = settings.getString("WocheBStundenListe", null);
            }

            WocheBStundenListe = gson.fromJson(bjson, type);
        }

        Stunde=  ((pos/5)-((pos/5)%1));
        orgStunde = Stunde;

        if(pos>=10 && !MemoryStundenListe.get(pos-5).isFreistunde() && (pos<MaxStunden*5-5)){
            if((Stunde%2==0 || !(MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos).getFach()))) && MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos-10).getFach())){
                pos = pos-5;
                Stunde = Stunde-1;
            }
        }

        switch (pos%5){
            case 0:
                Wochentag = "Montag";
                break;
            case 1:
                Wochentag = "Dienstag";
                break;
            case 2:
                Wochentag = "Mittwoch";
                break;
            case 3:
                Wochentag = "Donnerstag";
                break;
            case 4:
                Wochentag = "Freitag";
                break;
            default:
                Wochentag = "Error: Modulo 5 >= 5";
        }
        DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+" Stunde");


        if(pos<MaxStunden*5-5){
            if(MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos).getFach()) && !MemoryStundenListe.get(pos-5).isFreistunde()){
                eDoppelstunde.setChecked(true);
                wasDopellstunde = true;
                DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ " & " + Integer.toString(Stunde+1)+" Stunde");
            }

        }




        if(MemoryStundenListe.get(pos-5).getFach().equals("Französisch") ||MemoryStundenListe.get(pos-5).getFach().equals("Spanisch") || MemoryStundenListe.get(pos-5).getFach().equals("Italiensich") || MemoryStundenListe.get(pos-5).getFach().equals("Niederländisch")){
            cUnterichtstartEingabe.setVisibility(View.VISIBLE);
            eUnterichtbegin.setText(Integer.toString(MemoryStundenListe.get(pos-5).getStartJahr()));
        }
        else{
            cUnterichtstartEingabe.setVisibility(View.GONE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Fächer);

        final AutoCompleteTextView Fach = (AutoCompleteTextView) findViewById(R.id.FachTextView);

        Fach.setAdapter(adapter);
        Fach.setThreshold(1);


        if(!zweiWöchentlich){
            cWiederholungAuswahl.setVisibility(View.GONE);
        }

        if (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            cMündlichSchriftlichAuswahl.setVisibility(View.GONE);
            cKursEingabe.setVisibility(View.GONE);


        }
        else if(!(MemoryStundenListe.get(pos-5).getKursnummer()==0)){
            eKursnummer.setText(Integer.toString(MemoryStundenListe.get(pos-5).getKursnummer()));
        }


        if ((Stufe.equals("Q1") || Stufe.equals("Q2"))){
            kursarten.add("Leistungskurs");
            kursarten.add("Projektkurs");
            kursarten.add("Zusatzkurs");
        }

        if(Stufe.equals("EF") || Stufe.equals("Q1")){
            kursarten.add("Vertiefungsfach");
        }


        List<String> Wochenwiederholung = new ArrayList<String>();

        if(zweiWöchentlich){
        if(pos<MaxStunden*5-5){
            if(!(MemoryStundenListe.get(pos-5).isFreistunde()) && ((!eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())) ||(eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())&& MemoryStundenListe.get(pos).getFach().equals(WocheBStundenListe.get(pos).getFach())))){
                wasWöchentlich = true;
                Wochenwiederholung.add("Jede Woche");
                Wochenwiederholung.add("Alle 2 Wochen");
            }else{
                Wochenwiederholung.add("Alle 2 Wochen");
                Wochenwiederholung.add("Jede Woche");
            }
        }else{
            Wochenwiederholung.add("Alle 2 Wochen");
            Wochenwiederholung.add("Jede Woche");
        }}


        hinweis = false;

        List<String> MündlichSchriftlich = new ArrayList<String>();

        if(!MemoryStundenListe.get(pos-5).isFreistunde()){
        if(MemoryStundenListe.get(pos-5).isSchriftlich()){
            MündlichSchriftlich.add("Schriftlich");
            MündlichSchriftlich.add("Mündlich");
        }
        else {
            MündlichSchriftlich.add("Mündlich");
            MündlichSchriftlich.add("Schriftlich");
        }
        }else {
            hinweis = true;
            MündlichSchriftlich.add("Mündl. / Schrift.");
            MündlichSchriftlich.add("Schriftlich");
            MündlichSchriftlich.add("Mündlich");
        }

        List<String> RaumSchule = new ArrayList<>();

        List<String> Halle = new ArrayList<>();
        Halle.add("kleine Halle");
        Halle.add("große Halle");

        ArrayAdapter<String> MündlichSchrifltlichAdapter;

        if(!(MemoryStundenListe.get(pos-5).getRaum().equals(""))){
            if(MemoryStundenListe.get(pos-5).getRaum().substring(1).matches("[0-9]+")){
                eRaum.setText(MemoryStundenListe.get(pos-5).getRaum().substring(1));
                RaumSchule.add("Raum");
                RaumSchule.add("Schule");
                eRaum.setVisibility(View.VISIBLE);
                eSchule.setVisibility(View.GONE);

            }else {
                eSchule.setText(MemoryStundenListe.get(pos-5).getRaum());
                RaumSchule.add("Schule");
                RaumSchule.add("Raum");

                eRaum.setVisibility(View.GONE);
                eSchule.setVisibility(View.VISIBLE);
                if(!(MemoryStundenListe.get(pos-5).getRaum().equals(""))){
                    eSchule.setText("Rats");
                }else {
                    eSchule.setText(MemoryStundenListe.get(pos-5).getRaum());
                }

            }



        }
        else{
            RaumSchule.add("Raum");
            RaumSchule.add("Schule");
        }


        eLehrer.setText(MemoryStundenListe.get(pos-5).getLehrer());
        Fach.setText(MemoryStundenListe.get(pos-5).getFach());


        cHalleEingabe.setVisibility(View.GONE);

        ArrayAdapter HalleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, Halle);
        HalleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter RaumSchuleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, RaumSchule);
        RaumSchuleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter KursartAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,kursarten);
        KursartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final boolean finalHinweis = hinweis;
        MündlichSchrifltlichAdapter = new ArrayAdapter<String>(getBaseContext(),R.layout.spinner_item,MündlichSchriftlich){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    if(finalHinweis){
                        tv.setTextColor(Color.GRAY);
                    }

                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        MündlichSchrifltlichAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final ArrayAdapter WiederholungApapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,Wochenwiederholung);
        WiederholungApapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sHalle.setAdapter(HalleAdapter);
        sRaumSchule.setAdapter(RaumSchuleAdapter);
        MündlichSchriftlichSpinner.setAdapter(MündlichSchrifltlichAdapter);
        WiederholungsSpinner.setAdapter(WiederholungApapter);
        KursartenSpinner.setAdapter(KursartAdapter);





        sRaumSchule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sRaumSchule.getSelectedItem().toString().equals("Schule")){
                    eRaum.setVisibility(View.GONE);
                    eSchule.setVisibility(View.VISIBLE);
                    eSchule.setText("Rats");
                }
                else{
                    eRaum.setVisibility(View.VISIBLE);
                    eSchule.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        eDoppelstunde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ " & " + Integer.toString(Stunde+1)+" Stunde");
                }
                else
                {
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(orgStunde)+" Stunde");
                }
            }}
        );


        ImageButton delete = findViewById(R.id.delete);
        delete.setImageResource(R.drawable.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean Doppelstunde = eDoppelstunde.isChecked();
                String Wiederholung = WiederholungsSpinner.getSelectedItem().toString();


                SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
                Gson gson = new Gson();
                String json = settings.getString("Stundenliste", null);
                String bjson = settings.getString("Stundenliste", null);
                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                MemoryStundenListe = gson.fromJson(json , type);
                WocheBStundenListe = gson.fromJson(bjson, type);

                MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "", 0, false, 0,null));
                WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "", 0, false, 0,null));



                if(Doppelstunde){
                    MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                }
                SharedPreferences.Editor editor = settings.edit();
                String jsona = gson.toJson( MemoryStundenListe);
                String jsonb = gson.toJson(WocheBStundenListe);


                if(Wiederholung=="Jede Woche") {
                    editor.putString("Stundenliste", jsona);
                    editor.putString("WocheBStundenListe", jsonb);
                }
                else{

                    if(Woche==1){
                        editor.putString("Stundenliste", jsona);
                    }
                    else{
                        editor.putString("WocheBStundenListe",  jsonb);
                    }
                }




                editor.apply();
                Intent i = new Intent(create_stundenplan_stunden_obtionen.this, create_stundenplan.class);
                i.putExtra("Woche", Woche);
                startActivity(i);

            }
        });




        ImageButton save = findViewById(R.id.save);
        save.setImageResource(R.drawable.tick);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              fach = Fach.getText().toString();
                Kursart = KursartenSpinner.getSelectedItem().toString();
                Unterichtbegin = eUnterichtbegin.getText().toString();
                Bewertung = MündlichSchriftlichSpinner.getSelectedItem().toString();
                Lehrer = eLehrer.getText().toString();
                Doppelstunde = eDoppelstunde.isChecked();
                if(zweiWöchentlich){
                    Wiederholung = WiederholungsSpinner.getSelectedItem().toString();
                }else{
                    Wiederholung = "Alle 2 Wochen";
                }

                StartJahr = Integer.parseInt("0"+eUnterichtbegin.getText().toString());
                Schriftlich = false;




               if(Bewertung == "Schriftlich" || (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && (fach.equals("Deutsch") || fach.equals("Englisch") || fach.equals("Mathe") || fach.equals("MathePhysikInformatik") || fach.equals("BioChemie") || fach.equals("Spanisch") || fach.equals("Französisch") || fach.equals("Latein")))){
                   Schriftlich=true;
               }

               if(
                            (
                                     !(fach=="Spanisch" || fach=="Französisch" || fach=="Italiensich" || fach=="Niederländisch" )
                               ||
                                     ((fach=="Spanisch" || fach=="Französisch" || fach=="Italiensich" || fach=="Niederländisch" )&&!(Unterichtbegin==""))
                            )
                       &&
                            (
                                    !(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))
                               ||
                                    ((Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && !(eKursnummer.getText().toString().equals("")) && !(Bewertung == "Mündl. / Schrift."))
                            )
                       &&
                            !(fach=="")
                  )

               {

                   kursname = (MemoryStundenListe.get(pos-5).getKürzel());

                   if(!Arrays.asList(Fächer).contains(fach) &&  (kursname == null ||kursname.equals(""))){

                       final AlertDialog.Builder mBuilder = new AlertDialog.Builder(create_stundenplan_stunden_obtionen.this);
                       View mView = getLayoutInflater().inflate(R.layout.create_stundenplan_fachnichtbekannt_alert, null);
                       final EditText Kursname = (EditText) mView.findViewById(R.id.Kursname);
                       Button Okay = (Button) mView.findViewById(R.id.Button);
                       mBuilder.setView(mView);
                       final AlertDialog dialog = mBuilder.create();
                       Okay.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                                if(!Kursname.getText().toString().equals("")){
                                    kursname = Kursname.getText().toString();
                                }
                                dialog.dismiss();
                                DatenSpeichern();

                           }
                       });
                       dialog.show();

                   }else {
                       DatenSpeichern();
                   }

               }
               else
               {
                      toast = Toast.makeText(create_stundenplan_stunden_obtionen.this, "Du hast nicht alle Pflichtfleder ausgefüllt"+fach, Toast.LENGTH_SHORT);
                      toast.show();
               }

            }
        });



        Fach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               
                if(Fach.getText().toString().equals("Französisch") ||Fach.getText().toString().equals("Spanisch") || Fach.getText().toString().equals("Italiensich") || Fach.getText().toString().equals("Niederländisch")){
                    cUnterichtstartEingabe.setVisibility(View.VISIBLE);
                }
                else{
                    cUnterichtstartEingabe.setVisibility(View.GONE);
                }

                if(Fach.getText().toString().equals("Sport")){
                    cHalleEingabe.setVisibility(View.VISIBLE);
                    cRaumEingabe.setVisibility(View.GONE);
                }
                else
                {cHalleEingabe.setVisibility(View.GONE);
                cRaumEingabe.setVisibility(View.VISIBLE);}
            }
        });

    }









    private void DatenSpeichern(){
        if(sRaumSchule.getSelectedItem().toString().equals("Schule")){
            Raum = eSchule.getText().toString();
        }
        else{
            Raum = eRaum.getText().toString();
            if(!(Raum.equals(""))){
                Raum="R"+Raum;
            }
        }


        if ((Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            Kursnummer = Integer.parseInt(eKursnummer.getText().toString());
        }
        else
        {
            Kursnummer = 0;
        }

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
        Gson gson = new Gson();
        String json = settings.getString("Stundenliste", null);
        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();


        if(zweiWöchentlich){
            String bjson = settings.getString("WocheBStundenListe", null);
            WocheBStundenListe = gson.fromJson(bjson, type);
        }



        MemoryStundenListe = gson.fromJson(json , type);


        if(fach.equals("Sport")){
            if(sHalle.getSelectedItem().toString().equals("kleine Halle")) {
                Raum = "kl H";
            }
            else{
                Raum = "gr H";
            }
        }

        switch (fach){
            case "Deutsch":
                fachkürzel = "D";
                break;
            case "Englisch":
                fachkürzel = "E";
                break;
            case "Französisch":
                fachkürzel = "Fr";
                break;
            case "Latein":
                fachkürzel = "La";
                break;
            case "Spanisch":
                fachkürzel = "Spa";
                break;
            case "Italiensich":
                fachkürzel = "It";
                break;
            case "Niederländisch":
                fachkürzel = "Ni";
                break;
            case "Kunst":
                fachkürzel = "Ku";
                break;
            case "Musik":
                fachkürzel = "Mu";
                break;
            case "Literatur":
                fachkürzel = "Li";
                break;
            case "Geschichte":
                fachkürzel = "Ge";
                break;
            case "Sozialwissenschaften":
                fachkürzel = "Sow";
                break;
            case "Erdkunde":
                fachkürzel = "Erd";
                break;
            case "Philosophie":
                fachkürzel = "Phi";
                break;
            case "Pädagogik":
                fachkürzel = "Pä";
                break;
            case "Religion (Ev.)":
                fachkürzel = "Re";
                break;
            case"Religion (Kath.)":
                fachkürzel = "Re";
                break;
            case "Mathe":
                fachkürzel = "M";
                break;
            case "Biologie":
                fachkürzel = "Bi";
                break;
            case "Physik":
                fachkürzel = "Ph";
                break;
            case "Chemie":
                fachkürzel = "Ch";
                break;
            case "Informatik":
                fachkürzel = "If";
                break;
            case "Sport":
                fachkürzel = "Sp";
                break;
            case "Politik":
                fachkürzel = "Pol";
                break;
            default:
                if(fach.length()>=3){
                    fachkürzel = fach.substring(0,2);
                }else{
                    fachkürzel = fach;
                }

                break;


        }






        SharedPreferences.Editor editor = settings.edit();

        if(Wiederholung=="Jede Woche") {

            if(!Doppelstunde && wasDopellstunde) {
                if (pos == orgPos) {
                    MemoryStundenListe.set(pos, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    WocheBStundenListe.set(pos, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                } else if (pos < orgPos) {
                    MemoryStundenListe.set(pos - 5, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    WocheBStundenListe.set(pos - 5, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    pos = pos + 5;
                }
            }


            MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr, kursname));
            WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr,kursname));

            if(Doppelstunde){
                MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
                WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
            }

            String jsonA = gson.toJson( MemoryStundenListe);
            String jsonB = gson.toJson(WocheBStundenListe);

            editor.putString("Stundenliste", jsonA);
            editor.putString("WocheBStundenListe", jsonB);
        }
        else{




            if(Woche==1){

                if(wasWöchentlich){
                    WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    if(Doppelstunde){
                        WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                }

                if(!Doppelstunde && wasDopellstunde){
                 if(pos==orgPos){
                     MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                 }
                 else if(pos<orgPos){
                     MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                     pos = pos+5;
                 }

                }
                MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr,kursname));

                if(Doppelstunde){
                    MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
                }


            }
            else{

                if(wasWöchentlich){
                    MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    if(Doppelstunde){
                        MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                }

                if(!Doppelstunde && wasDopellstunde){
                    if(pos==orgPos){
                        WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                    else if(pos<orgPos){
                        WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                        pos = pos+5;
                    }

                }
                if(Doppelstunde){
                    WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich, StartJahr,kursname));
                }

                WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich, StartJahr,kursname));

            }

            String jsonA = gson.toJson( MemoryStundenListe);
            editor.putString("Stundenliste", jsonA);
            String jsonB = gson.toJson(WocheBStundenListe);
            editor.putString("WocheBStundenListe", jsonB);

        }

        editor.apply();
        Intent i = new Intent(create_stundenplan_stunden_obtionen.this, create_stundenplan.class);
        i.putExtra("Woche", Woche);
        i.putExtra("Stufe", settings.getString("Stufe", null));
        startActivity(i);
    }
    }



