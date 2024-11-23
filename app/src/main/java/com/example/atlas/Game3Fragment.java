package com.example.atlas;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.atlas.databinding.FragmentGame3Binding;
import com.example.atlas.models.Pais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fragment que muestra el juego 3
 * Dada la bandera y el nombre de un pais, el usuario debe adivinar la capital del pais
 */
public class Game3Fragment extends Fragment {

    private FragmentGame3Binding binding;
    private List<Pais> paisList; // Lista de países
    private Pais paisCorrecto; // País correcto
    private int streakCount = 0; // Contador de aciertos seguidos
    private int maxScore = 0; // Puntuación máxima
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGame3Binding.inflate(inflater, container, false);

        // Inicializar el ViewModel
        PaisViewModel viewModel = new ViewModelProvider(this).get(PaisViewModel.class);
        viewModel.fetchPaisesList(195, 0);

        // Observar la lista de países
        viewModel.getPaisListLiveData().observe(getViewLifecycleOwner(), paisList -> {
            if (paisList == null || paisList.isEmpty()) {
                Toast.makeText(getContext(), "No se pudieron obtener países", Toast.LENGTH_SHORT).show();
                return;
            }
            this.paisList = paisList;
            setupGame(); // Configurar el juego inicial
        });
        return binding.getRoot();
    }

    /**
     * Metodo que configura el juego
     */
    private void setupGame() {
        // Seleccionar un país aleatorio como el país correcto
        paisCorrecto = selectRandomPais(paisList);

        // Verificar que la URL de la bandera y el nombre del pais sean validos
        if (paisCorrecto.getFlags().getPng() != null && paisCorrecto.getTranslations().getSpa().getCommon() != null) {
            Glide.with(this).load(paisCorrecto.getFlags().getPng()).into(binding.ivBandera);
            binding.tvNombrePais.setText(paisCorrecto.getTranslations().getSpa().getCommon());
        } else {
            Log.e("Game3Fragment", "No se encontró la URL de la bandera.");
        }

        // Generar las opciones (1 correcta y 3 incorrectas)
        List<Pais> opciones = generateOptions(paisCorrecto, paisList);

        // Asignar las opciones a los botones
        Button[] optionButtons = {binding.option1, binding.option2, binding.option3, binding.option4};
        for (int i = 0; i < optionButtons.length; i++) {
            Button button = optionButtons[i];
            List<String> capitales = opciones.get(i).getCapital();
            String capital = String.join(", ", capitales); // Une los elementos sin corchetes
            button.setText(capital);
            button.setBackgroundColor(Color.LTGRAY); // Restablecer el color
            button.setEnabled(true); // Habilitar los botones
            setOptionButtonClickListener(button, opciones.get(i));
        }

        updateScoreboard(); // Actualizar los contadores
    }

    /**
     * Metodo que configura el click de los botones de opción
     * @param button boton que se ha pulsado
     * @param pais capital que se ha pulsado
     */
    private void setOptionButtonClickListener(Button button, Pais pais) {
        button.setOnClickListener(v -> {
            // Deshabilitar botones para evitar múltiples clics
            disableButtons();

            // Comprobar si la respuesta es correcta
            if (pais.equals(paisCorrecto)) {
                button.setBackgroundColor(Color.GREEN); // Correcto
                streakCount++; // Incrementar racha
                Toast.makeText(getContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
            } else {
                button.setBackgroundColor(Color.RED); // Incorrecto
                streakCount = 0; // Resetear racha
                Toast.makeText(getContext(), "Incorrecto", Toast.LENGTH_SHORT).show();
            }

            // Actualizar la puntuación máxima si es necesario
            if (streakCount > maxScore) {
                maxScore = streakCount;
            }

            updateScoreboard(); // Actualizar la vista de contadores

            // Retrasar el inicio de un nuevo juego
            handler.postDelayed(this::setupGame, 1500); // 1.5 segundos de espera
        });
    }

    /**
     * Metodo que actualiza los contadores
     */
    private void updateScoreboard() {
        binding.tvStreakCount.setText(String.valueOf(streakCount));
        binding.tvMaxScore.setText(String.valueOf(maxScore));
    }

    private void disableButtons() {
        // Deshabilitar todos los botones de opción
        binding.option1.setEnabled(false);
        binding.option2.setEnabled(false);
        binding.option3.setEnabled(false);
        binding.option4.setEnabled(false);
    }

    /**
     * Metodo que genera las opciones
     * @param paisCorrecto pais correcto
     * @param paisList lista de países
     * @return lista de 4 opciones
     */
    private List<Pais> generateOptions(Pais paisCorrecto, List<Pais> paisList) {
        List<Pais> opciones = new ArrayList<>();
        opciones.add(paisCorrecto);

        // Agregar 3 países incorrectos
        while (opciones.size() < 4) {
            Pais paisAleatorio = selectRandomPais(paisList);
            if (!opciones.contains(paisAleatorio)) {
                opciones.add(paisAleatorio);
            }
        }
        Collections.shuffle(opciones); // Mezclar las opciones
        return opciones;
    }

    /**
     * Metodo que selecciona un pais aleatorio
     * @param paisList lista de países
     * @return pais aleatorio
     */
    private Pais selectRandomPais(List<Pais> paisList) {
        return paisList.get((int) (Math.random() * paisList.size()));
    }
}
