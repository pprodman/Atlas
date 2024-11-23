package com.example.atlas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atlas.databinding.FragmentHomeBinding;
import com.example.atlas.databinding.ViewholderRecyclerviewBinding;
import com.example.atlas.models.Pais;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private PaisViewModel viewModel;
    private ContenidosAdapter adapter;

    // Inicializa el fragmento
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // Configura la vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PaisViewModel.class);
        adapter = new ContenidosAdapter();
        binding.contenidos.setAdapter(adapter);

        viewModel.getPaisListLiveData().observe(getViewLifecycleOwner(), pokemonList -> {
            if (pokemonList != null) {
                adapter.setPaisList(pokemonList);
            }
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e("HomeFragment", error);
                // You might want to show this error to the user
            }
        });

        // SearchView listener
        binding.svTexto.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchPais(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchPais(newText);
                return true;
            }
        });

        // Fetch initial list of Paises (NO LO USO)
        viewModel.fetchPaisesList(20, 0);
    }

    // Destruye la vista
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // ADAPTADOR DE LA LISTA DE CONTENIDOS
    static class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder> {

        private List<Pais> paisList = new ArrayList<>();

        // Crea el ViewHolder
        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        // Bind datos en el ViewHolder
        @Override
        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
            Pais pais = paisList.get(position);
            holder.binding.tvNombre.setText(pais.getName().getCommon());
            Glide.with(holder.itemView.getContext()).load(pais.getFlags().getPng()).into(holder.binding.ivBandera);
        }

        // Devuelve el número de elementos en la lista
        @Override
        public int getItemCount() {
            return paisList.size();
        }

        // Actualiza la lista de Pokémon
        void setPaisList(List<Pais> paisList) {
            this.paisList = paisList;
            notifyDataSetChanged();
        }
    }

    // VIEWHOLDER DE LA LISTA DE CONTENIDOS
    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderRecyclerviewBinding binding;

        public ContenidoViewHolder(@NonNull ViewholderRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}

