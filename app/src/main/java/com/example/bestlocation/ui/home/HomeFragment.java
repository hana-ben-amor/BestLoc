package com.example.bestlocation.ui.home;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bestlocation.Config;
import com.example.bestlocation.JSONParser;
import com.example.bestlocation.Position;
import com.example.bestlocation.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Position> data= new ArrayList<Position>();

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //connexion internet => thread
                Telechargement t=new Telechargement();
                t.execute(); //lancement du thread

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class Telechargement extends AsyncTask
    {

        AlertDialog alert;


        @Override
        protected void onPreExecute() {
            //UI thread
            //getActivity() trj3lna l activity mtaa l Fragment
            AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
            dialog.setTitle("Téléchargement");
            dialog.setMessage("Veuillez patienter...");

            alert=dialog.create();
            alert.show();



        }

        @Override
        protected Object doInBackground(Object[] objects) {
            //le second thread: n'a pas accès à l'interface
            JSONParser parser=new JSONParser();
            //HashMap<String,String> params=new HashMap()<>;
            //JSONObject response=parser.makeHttpRequest(Config.Url_GetAll,"GET",params);
            JSONObject response=parser.makeRequest(Config.Url_GetAll);
            try {
                int success=response.getInt("success");
                if (success==1)
                {
                    JSONArray tableau=response.getJSONArray("positions");
                    data.clear();
                    for(int i=0;i< tableau.length();i++)
                    {
                        JSONObject ligne=tableau.getJSONObject(i);
                        int idposition=ligne.getInt("idposition");
                        String pseudo=ligne.getString("pseudo");
                        String numero=ligne.getString("numero");
                        String longitude=ligne.getString("longitude");
                        String latitude=ligne.getString("latitude");
                        data.add(new Position(idposition,latitude,longitude,numero,pseudo));
                        //retrofit plugin fl java

                    }
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            alert.dismiss();
            binding.lv.setAdapter(new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    data
            ));

        }
    }


    //travail a faire pour cette application :
    //l application hethy aktharyet l note aliha
    //pour cette app fenetre okhra fiha le num poseudo  w longit w latitude kol taht baathhom f col whda w capter la modification de l'emplation chaque min wla chaque 10 metre
    //w 3 outon louta back , MAP , ADD bl map tkhtar position directement depuis le map
    //pour l'affichage des donnes pour chaque positiin on peut la supprimer affocher dan sle map ou lui evoyer un sms
}