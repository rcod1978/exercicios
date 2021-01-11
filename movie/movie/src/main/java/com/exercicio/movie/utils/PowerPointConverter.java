package com.exercicio.movie.utils;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import com.exercicio.movie.response.Movie;

public class PowerPointConverter {

	public static OutputStream convertMovieToFileOutputStream (List<Movie> movies) throws IOException {
		OutputStream out = new FileOutputStream(Constants.NOME_ARQUIVO);
		//creating a presentation
	      XMLSlideShow ppt = new XMLSlideShow();
	      if(movies != null && movies.size() > 0) {
	    	  XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
	    	  for(Movie movie : movies) {
		    	//creating a slide in it
	    		  XSLFSlideLayout titleLayout = defaultMaster.getLayout(SlideLayout.TITLE);
		          XSLFSlide slide = ppt.createSlide(titleLayout);
		          XSLFTextShape title = slide.getPlaceholder(0);
		          title.clearText();
		          /*title.addNewTextParagraph().addNewTextRun().setText("Nome do filme: "+ movie.getNomeFilme());
		          title.addNewTextParagraph().addNewTextRun().setText("Realizador: "+ movie.getRealizador());
		          title.addNewTextParagraph().addNewTextRun().setText("Ano de Lançamento: "+ movie.getAnoLancamento());
		          title.addNewTextParagraph().addNewTextRun().setText("Protagonistas: "+ movie.imprimeProtagonistas());*/
		          XSLFTextParagraph paragraphNome = title.addNewTextParagraph();	
		          
		          XSLFTextRun nomeFilme = paragraphNome.addNewTextRun();
		          nomeFilme.setText("Nome do filme: "+ movie.getNomeFilme());
		          nomeFilme.setFontColor(Color.decode("#000000"));
		          nomeFilme.setFontSize(20.);
		          nomeFilme.setBaselineOffset(1.0);
		          
		          XSLFTextParagraph paragraphRealizador = title.addNewTextParagraph();	
		          XSLFTextRun realizador = paragraphRealizador.addNewTextRun();
		          realizador.setText("Realizador: "+ movie.getRealizador());
		          realizador.setFontColor(Color.decode("#000000"));
		          realizador.setFontSize(20.);
		          realizador.setBaselineOffset(1.0);
		          
		          XSLFTextParagraph paragraphAno = title.addNewTextParagraph();	
		          XSLFTextRun anoLancamento = paragraphAno.addNewTextRun();
		          anoLancamento.setText("Ano de Lançamento: "+ movie.getAnoLancamento());
		          anoLancamento.setFontColor(Color.decode("#000000"));
		          anoLancamento.setFontSize(20.);
		          anoLancamento.setBaselineOffset(1.0);
		          
		          XSLFTextParagraph paragraphProtagonistas = title.addNewTextParagraph();	
		          XSLFTextRun protagonistas = paragraphProtagonistas.addNewTextRun();
		          protagonistas.setText("Protagonistas: "+ movie.imprimeProtagonistas());
		          protagonistas.setFontColor(Color.decode("#000000"));
		          protagonistas.setFontSize(15.);
		          
		          title.setAnchor(new Rectangle(50,50,500,500));
		      }
	      }
        ppt.write(out);
        out.close();
        ppt.close();
        return out;
	}
}
