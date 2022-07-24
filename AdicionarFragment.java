package br.com.dlweb.lvm.conteudo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.dlweb.lvm.R;
import br.com.dlweb.lvm.conteudo.Conteudo;
import br.com.dlweb.lvm.database.DatabaseHelper;
import br.com.dlweb.lvm.unidade.ListarFragment;

public class AdicionarFragment extends Fragment {

    EditText etNome;
        Spinner spUnidade;
    Spinner spObjeto;
    ArrayList<Integer> listUnidadeId;
    ArrayList<String> listUnidadeName;
    ArrayList<Integer> listObjetoId;
    ArrayList<String> listObjetoName;
    DatabaseHelper databaseHelper;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.conteudo_fragment_adicionar, container, false);

        spUnidade = v.findViewById(R.id.spinnerUnidadeConteudo);
        spObjeto = v.findViewById(R.id.spinnerObjetoConteudo);
        etNome = v.findViewById(R.id.editTextNomeConteudo);
        

        databaseHelper = new DatabaseHelper(getActivity());

        listUnidadeId = new ArrayList<>();
        listUnidadeName = new ArrayList<>();
        databaseHelper.getAllNameUnidade(listUnidadeId, listUnidadeName);

        listObjetoId = new ArrayList<>();
        listObjetoName = new ArrayList<>();
        databaseHelper.getAllNameObjeto(listObjetoId, listObjetoName);

        ArrayAdapter<String> spUnidadeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listUnidadeName);
        spUnidade.setAdapter(spUnidadeArrayAdapter);

        ArrayAdapter<String> spObjetoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listObjetoName);
        spObjeto.setAdapter(spObjetoArrayAdapter);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarConteudo);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (spUnidade.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a Unidade!", Toast.LENGTH_LONG).show();
        } else if (spObjeto.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o Objeto!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe um nome para o conteúdo!", Toast.LENGTH_LONG).show();
        
        } else {
            Conteudo b = new Conteudo();
            String nomeUnidade = spUnidade.getSelectedItem().toString();
            b.setId_unidade(listUnidadeId.get(listUnidadeName.indexOf(nomeUnidade)));
            String nomeObjeto = spObjeto.getSelectedItem().toString();
            b.setId_objeto(listObjetoId.get(listObjetoName.indexOf(nomeObjeto)));
            b.setNome(etNome.getText().toString());
          
            databaseHelper.createConteudo(b);
            Toast.makeText(getActivity(), "Bebê salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_objeto, new ListarFragment()).commit();
        }
    }

}