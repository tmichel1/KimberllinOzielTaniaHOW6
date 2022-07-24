package br.com.dlweb.lvm.conteudo;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.dlweb.lvm.database.DatabaseHelper;
Conteudo d;

public class EditarFragment extends Fragment {

    EditText etNome;
    
    Spinner spUnidade;
    Spinner spObjeto;
    ArrayList<Integer> listUnidadeId;
    ArrayList<String> listUnidadeName;
    ArrayList<Integer> listObjetoId;
    ArrayList<String> listObjetoName;
    DatabaseHelper databaseHelper;
    Conteudo b;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.conteudo_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_Conteudo = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        b = databaseHelper.getByIdConteudo(id_Conteudo);

        spUnidade = v.findViewById(R.id.spinnerUnidadeConteudo);
        spObjeto = v.findViewById(R.id.spinnerObjetoConteudo);
        etNome = v.findViewById(R.id.editTextNomeConteudo);
        
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

        etNome.setText(b.getNome());
         spUnidade.setSelection(listUnidadeId.indexOf(b.getId_unidade()));
        spObjeto.setSelection(listObjetoId.indexOf(b.getId_objeto()));

        Button btnEditar = v.findViewById(R.id.buttonEditarConteudo);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_Conteudo);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirConteudo);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_conteudo);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_Conteudo);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe um nome para o conteúdo!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Conteudo b = new Conteudo();
            b.setId(id);
            String nomeUnidade = spUnidade.getSelectedItem().toString();
            b.setId_unidade(listUnidadeId.get(listUnidadeName.indexOf(nomeUnidade)));
            String nomeObjeto = spObjeto.getSelectedItem().toString();
            b.setId_objeto(listObjetoId.get(listObjetoName.indexOf(nomeObjeto)));
            b.setNome(etNome.getText().toString());
            
            databaseHelper.updateConteudo(b);
            Toast.makeText(getActivity(), "Conteúdo editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConteudo, new ListarFragment()).commit();
        }
    }
    private void excluir (int id) {
        b = new Conteudo();
        b.setId(id);
        databaseHelper.deleteConteudo(b);
        Toast.makeText(getActivity(), "Conteudo excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConteudo, new ListarFragment()).commit();
    }
}
