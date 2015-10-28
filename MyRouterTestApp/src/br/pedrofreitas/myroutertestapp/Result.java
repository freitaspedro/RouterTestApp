package br.pedrofreitas.myroutertestapp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.WindowManager;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.fragment.HeadlinesFragment;
import br.pedrofreitas.myroutertestapp.fragment.Info;
import br.pedrofreitas.myroutertestapp.fragment.ResultFragment;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Dado;

public class Result extends ActionBarActivity 
		implements HeadlinesFragment.OnHeadlineSelectedListener {
	
	private Map<String, String> finalResult = new LinkedHashMap<>();
	private Info mInfo = null; 
	
	private Dado dado;
	private static final String[] ataques = {"Reboot", "Dns", "Acesso_remoto", "Filtro_mac", "Abrir_rede"};		//usado para identificar os metodos
	private static final String[] headlines = {"Reboot", "DNS", "Acesso Remoto", "Filtro MAC", "Abrir Rede"};		//exibido
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_result);         

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        String breakline = System.getProperty("line.separator");
        
        dado = (Dado) getIntent().getSerializableExtra("Dado");
        
        
        String operadora = (dado.getOperadora() != null) 
											 ? "Roteador testado é da operadora " + dado.getOperadora() + "." + breakline
											 : "Não foi possível identificar a operadora do roteador testado." + breakline;
        String fabricante_modelo = (!dado.getFabricante_modelo().equals("nao identificado")) 
        												 ? "O firmware utilizado se assemelha aos dos roteadores " + dado.getFabricante_modelo() + "." + breakline 
        												 : "Fabricante e modelo de roteador desconhecido." + breakline;
        String usuario_senha = (!dado.getUsuario().equals("nao identificado") && !dado.getSenha().equals("nao identificada")) 
        												 ? "Só foi possível realizar esse procedimento pelo fato do roteador estar usando o login padrão definido pelo fabricante ou mesmo pela operadora, cujo usuário é '" 
        												 + dado.getUsuario() + "' e a senha '" + dado.getSenha() + "'. É fortemente recomendado que este login seja alterado." + breakline 
        												 : "Usuário e senha de acesso à administração não foram descobertos." + breakline;
        
        AtaqueDao mAtaqueDao = new AtaqueDao(this);
        Ataque mAtaque = null; 
        
        for(int i = 0; i < ataques.length; i++) {
        	Method method = null;
			try {
				method = dado.getClass().getMethod("get" + ataques[i] + "_ataque");
			} catch (NoSuchMethodException e) {
				Log.e("ERR_METHOD", "NoSuchMethodException", e);
			}        	
        	int id = 0;
			try {
				id = (int) method.invoke(dado);
			} catch (IllegalAccessException e) {
				Log.e("ERR_INVOKE_METHOD", "IllegalAccessException", e);
			} catch (IllegalArgumentException e) {
				Log.e("ERR_INVOKE_METHOD", "IllegalArgumentException", e);
			} catch (InvocationTargetException e) {
				Log.e("ERR_INVOKE_METHOD", "InvocationTargetException", e);
			}
        	
        	if(id != 0) {
        		mAtaque = mAtaqueDao.getAtaqueWithId(id);
        		
        		String espc = "";        		
        		switch(ataques[i]) {
        			case "Reboot":
        				espc = "Durante o teste foi realizado um reboot em seu roteador com sucesso. " + 
        					   "Abaixo são apresentadas algumas informações e discutidas falhas na segurança de seu roteador que permitiram a execução desse procedimento:" + breakline + breakline;
        				break;
        			case "Dns":
        				espc = "Durante o teste foram alterados os dns primário e secundário de seu roteador para os do Google (8.8.8.8 e 8.8.4.4). " + 
        					   "Abaixo são apresentadas algumas informações e discutidas falhas na segurança de seu roteador que permitiram a execução desse procedimento:" + breakline + breakline;
        				break;
        			case "Acesso_remoto":
        				espc = "Durante o teste foi possível liberar o acesso remoto à administração de seu roteador. " + 
         					   "Abaixo são apresentadas algumas informações e discutidas falhas na segurança de seu roteador que permitiram a execução desse procedimento:" + breakline + breakline;
        				break;
        			case "Filtro_mac":
        				espc = "Durante o teste foi habilitado o filtro por endereço MAC da rede sem fio, permitindo acesso à rede sem fio apenas pelo dispositivo em uso. " + 
         					   "Abaixo são apresentadas algumas informações e discutidas falhas na segurança de seu roteador que permitiram a execução desse procedimento:" + breakline + breakline;
        				break;
        			case "Abrir_rede":
        				espc = "Durante o teste, a segurança usada em sua rede sem fio foi removida, tornando a rede aberta. " + 
         					   "Abaixo são apresentadas algumas informações e discutidas falhas na segurança de seu roteador que permitiram a execução desse procedimento:" + breakline + breakline;
        				break;
        			default:
    					Log.e("ERR_TIPO", "Tipo de ataque nao identificado");
    					break;
        		}
        		
        		
        		String usa_cookie = (mAtaque.getUsa_cookie() != 0) 
        				? "Foi possível constatar a presença de cookies contendo o id da sessão em cada requisição realizada ao roteador durante esse procedimento. " + 
        				  "Apesar desse mecanismo ser comumente utilizado contra ataques CSRF (Cross-site request forgery), a API do Android nos fornece métodos" +
        				  " suficientes para que essa defesa seja contornada." + breakline 
        				: breakline;
        		
        		String num_padrao = "um padrão";
        		if(mAtaque.getFormato_chave_sessao() != null) {
	        		if(mAtaque.getFormato_chave_sessao().contains("||")) {
	        			num_padrao = "padrões";
	        			mAtaque.getFormato_chave_sessao().replace("||", "");        			
	        		}
        		}
        		
        		String usa_chave_sessao_formato = (mAtaque.getUsa_chave_sessao() != 0 && mAtaque.getFormato_chave_sessao() != null) 
        				? "A cada requisição considerada crítica é passado o valor da chave de sessão na URL. " + 
        				  "Este seria um método eficiente contra ataques CSRF, porém este valor de chave pode ser descoberto ao analisar o código retornado à " +
        				  "uma requisição imediatamente anterior. Além de ser visível esse valor também obece " + num_padrao + " facilitando ainda mais a sua descoberta, " + 
        				  "como podemos vê nesta linha de código: " + mAtaque.getFormato_chave_sessao() + "." + breakline
        				: breakline;
        		
        		String usa_so_get = (mAtaque.getUsa_so_get() != 0) ? "Em todas as requisições utilizadas nesse procedimento foi adotado o método GET, seria interessante " + 
        															 "que nas requisiçoes mais críticas se optasse por utilizar o método POST, evitando assim expor informações importantes " + 
        															 "na URL como acontece no método GET." + breakline 
        														   : breakline;
        	
        		finalResult.put(headlines[i], espc + operadora + fabricante_modelo + usuario_senha + usa_cookie + usa_chave_sessao_formato + usa_so_get);
        	} else {
        		finalResult.put(headlines[i], null);
        	}           
        }  
                
        mInfo = new Info(finalResult);        
        
       
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            
            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment(mInfo);

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }		
	
	 @Override
	protected void onStart() {
		 super.onStart();	 
	}
   
    @Override
	public void onResultSelected(int position) {
    	// The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        ResultFragment resultFrag = (ResultFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        
        if (resultFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
        	resultFrag.updateResultView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
        	ResultFragment newFragment = new ResultFragment(mInfo);
            Bundle args = new Bundle();
            args.putInt(ResultFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    
}
