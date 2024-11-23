package com.example.atlas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atlas.databinding.FragmentProjectBinding;

/**
 * Fragment que muestra el proyecto
 */
public class ProjectFragment extends Fragment {


    private FragmentProjectBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentProjectBinding.inflate(inflater, container, false)).getRoot();
    }
}