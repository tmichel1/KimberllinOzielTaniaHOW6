package br.com.dlweb.lvm;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MenuFragment extends Fragment {

    public MenuFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Verifica qual opção foi selecionada pelo usuário
        switch (item.getItemId()) {
            case R.id.menu_unidade:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.lvm.unidade.MainFragment()).commit();
                break;
            case R.id.menu_objeto:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.lvm.objeto.MainFragment()).commit();
                break;
            case R.id.menu_conteudo:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new br.com.dlweb.lvm.conteudo.MainFragment()).commit();
                break;
            
            
        }
        return super.onOptionsItemSelected(item);
    }
}
                