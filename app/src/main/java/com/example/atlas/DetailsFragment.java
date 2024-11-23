package com.example.atlas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.atlas.databinding.FragmentDetailsBinding;
import com.example.atlas.models.Pais;
import com.example.atlas.models.details.Currency;

import java.util.Map;

/**
 * Fragment que muestra los detalles de un país
 */
public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private PaisViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         binding = FragmentDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PaisViewModel.class);

        // Observar el país seleccionado
        viewModel.seleccionado().observe(getViewLifecycleOwner(), pais -> {
            if (pais != null) {
                Glide.with(requireContext()).load(pais.getFlags().getPng()).into(binding.ivBandera); //bandera
                binding.tvNombre.setText(pais.getTranslations().getSpa().getCommon()); //nombre español
                binding.tvCommonName.setText(pais.getName().getCommon()); //nombre común
                binding.tvOfficialName.setText(pais.getName().getOfficial()); //nombre oficial
                binding.tvRegion.setText(pais.getRegion()); //región
                binding.tvSubregion.setText(pais.getSubregion()); //subregión
                // Manejar múltiples capitales
                if (pais.getCapital() != null && !pais.getCapital().isEmpty()) {
                    String capitales = String.join(", ", pais.getCapital()); // Unir las capitales con una coma
                    binding.tvCapital.setText(capitales);
                } else {
                    binding.tvCapital.setText("Sin capital definida"); // Si no hay capitales
                }
                String coordenadas = pais.getLatlng().get(0) + ", " + pais.getLatlng().get(1);
                binding.tvLatLng.setText(coordenadas); //latitud y longitud
                binding.tvTimezone.setText(String.join(", ", pais.getTimezones())); //zona horaria
                String area = pais.getArea() + " km²";
                binding.tvArea.setText(area); //área
                binding.tvPopulation.setText(String.valueOf(pais.getPopulation()));//población
                // Manejar múltiples fronteras
                if (pais.getBorders() != null && !pais.getBorders().isEmpty()) {
                    String fronteras = String.join(", ", pais.getBorders()); // Unir las capitales con una coma
                    binding.tvLandBorders.setText(fronteras);
                } else {
                    binding.tvLandBorders.setText("Sin fronteras definidas"); // Si no hay fronteras
                }
                String idiomas = getLanguages(pais);
                binding.tvLanguages.setText(idiomas); //idiomas
                String money = getMoneda(pais);
                binding.tvCurrencies.setText(money); //monedas
                String dominios = String.join(", ", pais.getTld());
                binding.tvTld.setText(dominios);
            }
        });

        return binding.getRoot();
    }

    /**
     * Metodo que obtiene las monedas de un pais
     * @param pais
     * @return String con las monedas del pais
     */
    private static @NonNull String getMoneda(Pais pais) {
        Map<String, Currency> currencies = pais.getCurrencies();

        StringBuilder moneyBuilder = new StringBuilder();
        for (Map.Entry<String, Currency> entry : currencies.entrySet()) {
            String moneda = entry.getValue().getName();
            String simbolo = entry.getValue().getSymbol();
            moneyBuilder.append(moneda).append(" ").append(simbolo).append(", ");
        }
        // Eliminar la última coma y espacio
        if (moneyBuilder.length() > 0) {
            moneyBuilder.setLength(moneyBuilder.length() - 2);
        }

        String money = moneyBuilder.toString();
        return money;
    }

    /**
     * Metodo que obtiene los idiomas de un pais
     * @param pais
     * @return String con los idiomas del pais
     */
    private static @NonNull String getLanguages(Pais pais) {
        Map<String, String> languages = pais.getLanguages();

        StringBuilder languagesBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            String language = entry.getValue();
            languagesBuilder.append(language).append(", ");
        }
        // Eliminar la última coma y espacio
        if (languagesBuilder.length() > 0) {
            languagesBuilder.setLength(languagesBuilder.length() - 2);
        }

        String languagesString = languagesBuilder.toString();
        return languagesString;
    }
}
