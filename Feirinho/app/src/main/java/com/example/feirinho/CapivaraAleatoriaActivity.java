package com.example.feirinho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CapivaraAleatoriaActivity extends AppCompatActivity {
    private static final int QUANTIDADE_CURSOS_EXIBIR = 20;
    private static final int TEMPO_EXIBICAO_CURSOS_MS = 200;

    TextView txtNomeCurso;
    ImageButton btnVoltar;
    ImageView imgCapivara, imgPapelDeParede;
    VideoView videoPapelDeParede;
    Handler handler;

    int i = 0;

    List<String> listaCursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capivara_aleatoria);
        imgCapivara = findViewById(R.id.imgCapivara);
        txtNomeCurso = findViewById(R.id.txtNomeCurso);
        btnVoltar = findViewById(R.id.btnVoltar);
   //     videoPapelDeParede = findViewById(R.id.videoPapelDeParede);
        imgPapelDeParede = findViewById(R.id.imgPapelDeParede);

        handler = new Handler();

        inserirCursos();
        //inserirPapelDeParede();
        checarImagensPorCurso();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaMain();
            }
        });

        onViewCreated();
    }
   /* private void inserirPapelDeParede() {
        String videoPath;

        if (i % 2 == 0) {
            videoPath = "android.resource://" + getPackageName() + "/" + R.raw.fundo_biologicas; // Substitua "video_file" pelo nome do seu vídeo na pasta "res/raw"
        } else {
            videoPath = "android.resource://" + getPackageName() + "/" + R.raw.fundo_tecnologia; // Substitua "video_file" pelo nome do seu vídeo na pasta "res/raw"
        }
        Uri uri = Uri.parse(videoPath);
        videoPapelDeParede.setVideoURI(uri);
        videoPapelDeParede.start();
    }
    */


    public void onViewCreated() {
        //inicia uma thread, para que renderize a view, mas possa aleatorizar o curso
        new Thread(this::aleatorizarCurso).start();

    }

    @Override
    public void onBackPressed() {
        //clicou no botão de voltar, volta para a actyivity main
        voltarParaMain();
    }

    private void voltarParaMain() {
        Intent it = new Intent(CapivaraAleatoriaActivity.this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
        CapivaraAleatoriaActivity.this.finish();
    }

    /**
     * Função que pega a lista de cursos e aleatoriza a imagem e o curso que aparece na tela, animando até chegar no curso sorteado
     */
    private void aleatorizarCurso() {
        int proximoCurso = 0, ultimoCurso = 0;
        Random random = new Random();
        for (int i = 0; i < QUANTIDADE_CURSOS_EXIBIR; i++) {
            //TODO: melhorar a aleatorização dos cursos para evitar que apareça o mesmo curso mais de uma vez (refinamento)
            //(criar string com os cursos que já foram, e ir aleatorizando os outros (ver se isso não vai afetar o desempenho)


            //TODO 2: esconder o botão de voltar até terminar de aleatorizar os cursos
            while (proximoCurso == ultimoCurso) {
                //evita que exiba o mesmo curso 2 vezes seguidas
                proximoCurso = random.nextInt(listaCursos.size());
            }
            String curso = listaCursos.get(proximoCurso);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //só podemos mexer nos componentes da tela na thread principal
                    txtNomeCurso.setText(curso);
                    inserirPapelDeParede(curso);
                    String arquivo = converterCursoParaNomeArquivo(curso);
                    int idArquivo = getResources().getIdentifier(arquivo, "drawable", getPackageName());

                    if (idArquivo != 0) {
                        imgCapivara.setImageResource(idArquivo);
                    } else {
                        imgCapivara.setImageResource(R.drawable.capi_teste);
                        Log.d("teste", "O arquivo " + arquivo + " não existe");
                    }
                }
            });
            try {
                //espera um pouco antes de mostrar o próximo curso
                Thread.sleep(TEMPO_EXIBICAO_CURSOS_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ultimoCurso = proximoCurso;
        }

    }

    void inserirPapelDeParede(String curso) {
        // TODO refatorar para criar uma classe curso que terá um atributo setor
        // ou ainda

        if (curso.equalsIgnoreCase("Agronomia") || curso.equalsIgnoreCase("Engenharia Florestal")
                || curso.equalsIgnoreCase("Engenharia Industrial Madeireira") || curso.equalsIgnoreCase("Zootecnia")) {
            // Setor de Ciências Agrárias
            imgPapelDeParede.setImageResource(R.drawable.fundo_agrarias);

        } else if (curso.equalsIgnoreCase("Biomedicina") || curso.equalsIgnoreCase("Ciências Biológicas")
                || curso.equalsIgnoreCase("Educação Física") || curso.equalsIgnoreCase("Fisioterapia")) {
            // Setor de Ciências Biológicas
            imgPapelDeParede.setImageResource(R.drawable.fundo_biologicas);

        } else if (curso.equalsIgnoreCase("Ciência da Computação") || curso.equalsIgnoreCase("Análise e Desenvolvimento de Sistemas")
                || curso.equalsIgnoreCase("Estatística") || curso.equalsIgnoreCase("Expressão Gráfica")
                || curso.equalsIgnoreCase("Física") || curso.equalsIgnoreCase("Informática Biomédica")
                || curso.equalsIgnoreCase("Matemática") || curso.equalsIgnoreCase("Matemática Industrial")
                || curso.equalsIgnoreCase("Química")) {
            // Setor de Ciências Exatas
            imgPapelDeParede.setImageResource(R.drawable.fundo_exatas);

        } else if (curso.equalsIgnoreCase("Direito")) {
            // Setor de Ciências Jurídicas
            imgPapelDeParede.setImageResource(R.drawable.fundo_juridicas);

        } else if (curso.equalsIgnoreCase("Enfermagem") || curso.equalsIgnoreCase("Farmácia")
                || curso.equalsIgnoreCase("Medicina") || curso.equalsIgnoreCase("Nutrição")
                || curso.equalsIgnoreCase("Odontologia") || curso.equalsIgnoreCase("Terapia Ocupacional")) {
            // Setor de Ciências da Saúde
            imgPapelDeParede.setImageResource(R.drawable.fundo_saude);

        } else if (curso.equalsIgnoreCase("Administração") || curso.equalsIgnoreCase("Ciências Contábeis")
                || curso.equalsIgnoreCase("Ciências Econômicas") || curso.equalsIgnoreCase("Ciência e Gestão da Informação")) {
            // Setor de Ciências Sociais Aplicadas
            imgPapelDeParede.setImageResource(R.drawable.fundo_ciencias_sociais);

        } else if (curso.equalsIgnoreCase("Geografia") || curso.equalsIgnoreCase("Geologia")
                || curso.equalsIgnoreCase("Geomática (Engenharia Cartográfica)")) {
            // Setor de Ciências da Terra
            imgPapelDeParede.setImageResource(R.drawable.fundo_ciencias_da_terra);

        } else if (curso.equalsIgnoreCase("Pedagogia")) {
            // Setor de Educação
            imgPapelDeParede.setImageResource(R.drawable.fundo_educacao);

        } else if (curso.equalsIgnoreCase("Arquitetura e Urbanismo") || curso.equalsIgnoreCase("Engenharia Civil")
                || curso.equalsIgnoreCase("Engenharia Elétrica") || curso.equalsIgnoreCase("Engenharia Mecânica")
                || curso.equalsIgnoreCase("Engenharia Ambiental") || curso.equalsIgnoreCase("Engenharia de Produção")
                || curso.equalsIgnoreCase("Engenharia de Aquicultura") || curso.equalsIgnoreCase("Engenharia de Bioprocessos e Biotecnologia")
                || curso.equalsIgnoreCase("Engenharia de Alimentos") || curso.equalsIgnoreCase("Engenharia Química")) {
            // Setor de Tecnologia
            imgPapelDeParede.setImageResource(R.drawable.fundo_tecnologia);

        } else if (curso.equalsIgnoreCase("Administração Pública") || curso.equalsIgnoreCase("Agroecologia")
                || curso.equalsIgnoreCase("Artes") || curso.equalsIgnoreCase("Ciências")
                || curso.equalsIgnoreCase("Ciências Ambientais") || curso.equalsIgnoreCase("Educação do Campo")
                || curso.equalsIgnoreCase("Educação Física") || curso.equalsIgnoreCase("Geografia")
                || curso.equalsIgnoreCase("Gestão de Turismo") || curso.equalsIgnoreCase("Gestão e Empreendedorismo")
                || curso.equalsIgnoreCase("Gestão Imobiliária") || curso.equalsIgnoreCase("Linguagem e Comunicação")
                || curso.equalsIgnoreCase("Saúde Coletiva") || curso.equalsIgnoreCase("Serviço Social")
                || curso.equalsIgnoreCase("Artes Visuais")) {
            // Campus UFPR Litoral
            imgPapelDeParede.setImageResource(R.drawable.fundo_litoral);

        } else if (curso.equalsIgnoreCase("Oceanografia") || curso.equalsIgnoreCase("Ciências Exatas")
                || curso.equalsIgnoreCase("Engenharia Civil") || curso.equalsIgnoreCase("Engenharia de Aquicultura")
                || curso.equalsIgnoreCase("Engenharia Ambiental e Sanitária")) {
            // Campus Centro de Estudos do Mar
            imgPapelDeParede.setImageResource(R.drawable.fundo_estudos_do_mar);

        } else if (curso.equalsIgnoreCase("Agronomia") || curso.equalsIgnoreCase("Ciências Biológicas")
                || curso.equalsIgnoreCase("Engenharia de Aquicultura") || curso.equalsIgnoreCase("Engenharia de Bioprocessos e Biotecnologia")
                || curso.equalsIgnoreCase("Engenharia de Energia") || curso.equalsIgnoreCase("Licenciatura em Ciências Exatas")
                || curso.equalsIgnoreCase("Licenciatura em Computação") || curso.equalsIgnoreCase("Medicina Veterinária")
                || curso.equalsIgnoreCase("Computação (Licenciatura)")) {
            // Campus Palotina
            imgPapelDeParede.setImageResource(R.drawable.fundo_palotina);

        } else if (curso.equalsIgnoreCase("Análise e Desenvolvimento de Sistemas") || curso.equalsIgnoreCase("Comunicação Institucional")
                || curso.equalsIgnoreCase("Gestão de Qualidade") || curso.equalsIgnoreCase("Gestão Pública")
                || curso.equalsIgnoreCase("Luteria") || curso.equalsIgnoreCase("Negócios Imobiliários")
                || curso.equalsIgnoreCase("Produção Cênica") || curso.equalsIgnoreCase("Secretariado")
                || curso.equalsIgnoreCase("Petróleo e Gás") || curso.equalsIgnoreCase("Agente Comunitário de Saúde")) {
            // SEPT
            imgPapelDeParede.setImageResource(R.drawable.fundo_sept);
        } else if (curso.equalsIgnoreCase("Ciências Sociais") || curso.equalsIgnoreCase("História")
                || curso.equalsIgnoreCase("Filosofia") || curso.equalsIgnoreCase("Letras")
                || curso.equalsIgnoreCase("Letras Libras") || curso.equalsIgnoreCase("Psicologia")
                || curso.equalsIgnoreCase("Turismo")) {
            // Setor de Ciências Humanas, Letras e Artes
            imgPapelDeParede.setImageResource(R.drawable.fundo_ciencias_humanas);

        } else {
            Log.d("teste","O curso "+curso+" não foi encontrado");
        }
    }


    private void checarImagensPorCurso() {
        int qtdade_nomes_errados = 0;
        for (int i = 0; i < QUANTIDADE_CURSOS_EXIBIR; i++) {
            String curso = listaCursos.get(i);
            String arquivo = converterCursoParaNomeArquivo(curso);
            int idArquivo = getResources().getIdentifier(arquivo, "drawable", getPackageName());

            if (idArquivo == 0) {
                Log.d("teste", "O arquivo " + arquivo + " não existe");
                qtdade_nomes_errados++;
            }

            //inserirPapelDeParede(curso);
        }

        if (qtdade_nomes_errados == 0) {
            Log.d("teste", "Todos os cursos da lista tem arquivos e seus nomes estão corretos.");
        } else {
            Log.d("teste", qtdade_nomes_errados + " arquivos com nomes errados!");
        }
    }


    private void inserirCursos() {
        listaCursos.add("Administração");
        listaCursos.add("Administração Pública");
        listaCursos.add("Agente Comunitário de Saúde");
        listaCursos.add("Agroecologia");
        listaCursos.add("Agronomia");
        listaCursos.add("Análise e Desenvolvimento de Sistemas");
        listaCursos.add("Arquitetura e Urbanismo");
        listaCursos.add("Artes");
        listaCursos.add("Artes Visuais");
        listaCursos.add("Biomedicina");
        listaCursos.add("Ciência da Computação");
        listaCursos.add("Ciências");
        listaCursos.add("Ciências Ambientais");
        listaCursos.add("Ciências Biológicas");
        listaCursos.add("Ciências Contábeis");
        listaCursos.add("Ciências Econômicas");
        listaCursos.add("Ciências Exatas");
        listaCursos.add("Ciências Sociais");
        listaCursos.add("Computação (Licenciatura)");
        listaCursos.add("Comunicação Institucional");
        listaCursos.add("Design de Produto");
        listaCursos.add("Design Gráfico");
        listaCursos.add("Direito");
        listaCursos.add("Educação do Campo");
        listaCursos.add("Educação Física");
        listaCursos.add("Enfermagem");
        listaCursos.add("Engenharia Agrícola");
        listaCursos.add("Engenharia Ambiental");
        listaCursos.add("Engenharia Ambiental e Sanitária");
        listaCursos.add("Engenharia Cartográfica e de Agrimensura");
        listaCursos.add("Engenharia Civil");
        listaCursos.add("Engenharia de Alimentos");
        listaCursos.add("Engenharia de Aquicultura");
        listaCursos.add("Engenharia de Bioprocessos e Biotecnologia");
        listaCursos.add("Engenharia de Energia");
        listaCursos.add("Engenharia de Produção");
        listaCursos.add("Engenharia Elétrica");
        listaCursos.add("Engenharia Florestal");
        listaCursos.add("Engenharia Industrial Madeireira");
        listaCursos.add("Engenharia Mecânica");
        listaCursos.add("Engenharia Química");
        listaCursos.add("Estatística");
        listaCursos.add("Expressão Gráfica");
        listaCursos.add("Farmácia");
        listaCursos.add("Filosofia");
        listaCursos.add("Física");
        listaCursos.add("Fisioterapia");
        listaCursos.add("Geografia");
        listaCursos.add("Geologia");
        listaCursos.add("Gestão da Informação");
        listaCursos.add("Gestão de Qualidade");
        listaCursos.add("Gestão de Turismo");
        listaCursos.add("Gestão e Empreendedorismo");
        listaCursos.add("Gestão Imobiliária");
        listaCursos.add("Gestão Pública");
        listaCursos.add("História");
        listaCursos.add("História, Memória e Imagem");
        listaCursos.add("Informática Biomédica");
        listaCursos.add("Letras");
        listaCursos.add("Letras Libras");
        listaCursos.add("Linguagem e Comunicação");
        listaCursos.add("Luteria");
        listaCursos.add("Matemática");
        listaCursos.add("Matemática Industrial");
        listaCursos.add("Medicina");
        listaCursos.add("Medicina Veterinária");
        listaCursos.add("Música");
        listaCursos.add("Negócios Imobiliários");
        listaCursos.add("Nutrição");
        listaCursos.add("Oceanografia");
        listaCursos.add("Odontologia");
        listaCursos.add("Pedagogia");
        listaCursos.add("Petróleo e Gás");
        listaCursos.add("Produção Cultural");
        listaCursos.add("Psicologia");
        listaCursos.add("Publicidade e Propaganda");
        listaCursos.add("Química");
        listaCursos.add("Relações Públicas");
        listaCursos.add("Saúde Coletiva");
        listaCursos.add("Saúde Coletiva");
        listaCursos.add("Secretariado");
        listaCursos.add("Serviço Social");
        listaCursos.add("Terapia Ocupacional");
        listaCursos.add("Turismo");
        listaCursos.add("Zootecnia");
    }

    /**
     * Método que converte nomes de cursos com acentos, espaços e vírgulas, para o padrão de arquivo
     */
    private String converterCursoParaNomeArquivo(String curso) {
        curso = curso.toLowerCase();
        curso = Normalizer.normalize(curso, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        curso = curso.replaceAll(" ", "_");  //troca espaço por _
        curso = curso.replaceAll("[,)(]", "");  //remove vírgulas e parêntesis
        return curso;
    }
}