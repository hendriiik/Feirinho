package com.example.feirinho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CapivaraAleatoriaActivity extends AppCompatActivity {

    TextView txtNomeCurso;
    ImageButton btnVoltar;

    List<String> listaCursos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capivara_aleatoria);
        txtNomeCurso = findViewById(R.id.txtNomeCurso);
        btnVoltar = findViewById(R.id.btnVoltar);
        inserirCursos();
        aleatorizarCurso();

    }

    void inserirCursos() {
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

    void aleatorizarCurso() {
        Random random = new Random();
        int cursoId = random.nextInt(listaCursos.size());
        txtNomeCurso.setText(listaCursos.get(cursoId));
    }
}