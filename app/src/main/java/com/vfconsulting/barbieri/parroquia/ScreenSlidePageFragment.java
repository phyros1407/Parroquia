package com.vfconsulting.barbieri.parroquia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePageFragment extends Fragment {

    List<HorarioBean> horarios = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        ViewGroup root_view = (ViewGroup) inflater.inflate(
                R.layout.horarios_fragment, container, false);




        return root_view;
    }

}
