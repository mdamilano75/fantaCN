

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class TestFC24 {
	
	public static void main(String[] args) {
		ExcelXLSX excelGiocatori;
		try {
			excelGiocatori = new ExcelXLSX(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_quotazioni);
		    Sheet foglio                    = null;
		    Row riga                        = null;
		    Cell cella                      = null;
		    Iterator<?> righe               = null;
		    Iterator<?> celle               = null;

		    foglio  						= excelGiocatori.getWb().getSheetAt(0);
		    righe   						= foglio.rowIterator();

		    Map<String, Squadra> mapSquadre	= new HashMap<String, Squadra>();
		    Squadra actSquadra				= new Squadra();
		    Giocatore actGiocatore			= new Giocatore();
		    Portiere actPortiere			= null;
		    Difensore actDifensore			= null;
        	Centrocampista actCentrocampista= null;
        	Attaccante actAttaccante		= null; 
		    
		    String R ="", RM ="", nomeGiocatore ="", 
		    		nomeSquadra ="";
		    int Id =0, QtA =0, QtI =0;
		    
		    int actCella					= 0;
		    int actriga						= 0;
		    while( righe.hasNext()) {
		        riga    					= (Row) righe.next();
		        if (actriga<2){
		        	// SALTO LA PRIMA RIGA - E' DI INTESTAZIONE
		        	actriga++;
		        	continue;
		        }
		        actCella					= 0;
		        nomeSquadra					= "";
	            celle                   	= riga.cellIterator();
	            while(celle.hasNext()) {
	                cella 					= (Cell) celle.next();
                	// colonne:
                	// 0-Id 1-R 2-RM 3-Nome 4-Squadra 5-Qt.A 6-Qt.I 
                	// 7-Diff. 8-Qt.A M 9-Qt.I M
                	// 10-Diff.M 11-FVM 12-FVM M
                	if (actCella==0){
                		Id			= (int)Double.parseDouble(cella.toString());
                	}
                	if (actCella==1){
                		R			= cella.toString();
                	}
                	if (actCella==2){
                		RM			= cella.toString();
                	}
                	if (actCella==3){
                		nomeGiocatore		= cella.toString();
                	}
                	if (actCella==4){
                		nomeSquadra	= cella.toString().toUpperCase();
                	}
                	if (actCella==5){
                		QtA			= (int)Double.parseDouble(cella.toString());
                	}
                	if (actCella==6){
                		QtI 		= (int)Double.parseDouble(cella.toString());
                	}
	                actCella++;
		        }
	            // fine RIGA
	            if (!FrmStringa.strVoid(nomeSquadra)){
		            actSquadra					= mapSquadre.get(nomeSquadra); 
		            if (actSquadra==null){
		            	actSquadra				= new Squadra();
		            	mapSquadre.put(nomeSquadra, actSquadra);
		            }
		            if (R.equals("P")){
		            	actPortiere				= new Portiere();
		            	actSquadra.arrPor.add(actPortiere);
			            actGiocatore					= actPortiere; 
		            } else if (R.equals("D")){
		            	actDifensore			= new Difensore();
		            	actSquadra.arrDif.add(actDifensore);
		            	actGiocatore					= actDifensore;
		            } else if (R.equals("C")){
		            	actCentrocampista		= new Centrocampista();
		            	actSquadra.arrCen.add(actCentrocampista);
		            	actGiocatore					= actCentrocampista;
		            } else if (R.equals("A")){
		            	actAttaccante			= new Attaccante();
		            	actSquadra.arrAtt.add(actAttaccante);
		            	actGiocatore					= actAttaccante;
		            }
		            
		            actGiocatore.setId(Id);
		            actGiocatore.addRuoli(RM);
		            actGiocatore.setSquadra(nomeSquadra);
		            actGiocatore.setNome(nomeGiocatore);
		            actGiocatore.setQuotazioneAtt(QtA);
		            actGiocatore.setQuotazioneIni(QtI);

	            }
	            actriga++;
		    }

		    
		    ExcelXLSX excelStatistiche24	= new ExcelXLSX(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_statistiche24_25);
		    ExcelXLSX excelStatistiche23	= new ExcelXLSX(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_statistiche23_24);
		    ExcelXLSX excelStatistiche22	= new ExcelXLSX(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_statistiche22_23);

		    foglio                    		= null;
		    riga                        	= null;
		    cella                      		= null;
		    righe               			= null;
		    celle               			= null;

		    for (int actAnno = 2022; actAnno < 2025; actAnno++) {
		    	if (actAnno==2022){
				    foglio  						= excelStatistiche22.getWb().getSheetAt(0);
		    	} else if (actAnno==2023){
				    foglio  						= excelStatistiche23.getWb().getSheetAt(0);
		    	} if (actAnno==2024){
				    foglio  						= excelStatistiche24.getWb().getSheetAt(0);
		    	}
			    righe   						= foglio.rowIterator();

			    int	Pv=0,	Gf=0,	Gs=0,	Rp=0,	Rc=0,	R_piu=0,	R_meno=0,	Ass=0,	Amm=0,	Esp=0,	Au=0;
			    double	Mv=0.0,	Fm=0.0;
			    actriga							= 0;
			    while( righe.hasNext()) {
			        riga    					= (Row) righe.next();
			        if (actriga<2){
			        	// SALTO LA PRIMA RIGA - E' DI INTESTAZIONE
			        	actriga++;
			        	continue;
			        }
			        actCella					= 0;
		            celle                   	= riga.cellIterator();
		            while(celle.hasNext()) {
		                cella 					= (Cell) celle.next();
	                	// colonne:
	                	// 0-Id 1-R 2-RM 3-Nome 4-Squadra 5-Qt.A 6-Qt.I 
	                	// 7-Diff. 8-Qt.A M 9-Qt.I M
	                	// 10-Diff.M 11-FVM 12-FVM M
	                	if (actCella==0){
	                		Id			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==5){
	                		Pv			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==6){
	                		Mv			= Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==7){
	                		Fm			= Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==8){
	                		Gf			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==9){
	                		Gs			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==10){
	                		Rp 			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==11){
	                		Rc 			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==12){
	                		R_piu		= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==13){
	                		R_meno		= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==14){
	                		Ass			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==15){
	                		Amm			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==16){
	                		Esp			= (int)Double.parseDouble(cella.toString());
	                	}
	                	if (actCella==17){
	                		Au			= (int)Double.parseDouble(cella.toString());
	                	}
		                actCella++;
			        }
		            // fine RIGA
	            	actGiocatore		= prelevaCalciatoreDaID(mapSquadre, Id);
	            	if (actGiocatore!=null){
//	            		actMapStatistiche	= actGiocatore.getMapStatistiche();
//	            		actStatistica		= actMapStatistiche.get(2024);
//	            		if (actStatistica==null){
//	            			actStatistica	= 
//	            		}
	            		actGiocatore.addStatistica(actAnno, Pv, Gf, Gs, Rp, Rc, R_piu, R_meno, Ass, Amm, Esp, Au, Mv, Fm);
	            	} else {
//	            		System.err.println(actAnno+" "+Id+" NON TROVATO Stat");	
	            	}
		            actriga++;
			    }
		    }

		    
		    
		    String rigaLetta				="";
            BufferedReader br 				= null;
            
		    ////////////////////////////////////////////
		    /////////////// carico RIGORISTI E CALCI PIAZZATI
		    /*
		    String filePath_rigoristi		= FC_Varie.pathFile+"/"+FC_Varie.nomeFile_rigoristiCPiazzati;
            String rigaPrecLetta			="";
            BufferedReader br 				= new BufferedReader(new FileReader(filePath_rigoristi));
            boolean elaboraRigoristi		= false;
            boolean elaboraCalciPiazzati	= false;
            int quantiRigoristi				= 0;
            int quantiCalciPiazzati			= 0;
            while ((rigaLetta = br.readLine()) != null) {
            	if (rigaLetta.equals("Rigori")){
            		elaboraRigoristi		= true;
            		nomeSquadra				= rigaPrecLetta.toUpperCase();
            		actSquadra				= mapSquadre.get(nomeSquadra);
            	}
            	if (elaboraRigoristi){
        			while (elaboraRigoristi) {
        				rigaPrecLetta		=rigaLetta;
            			rigaLetta			= br.readLine();
            			if (!rigaLetta.contains("Campioncino")){
            				elaboraRigoristi= false;
            				break;
            			}
        				rigaPrecLetta=rigaLetta;
        				rigaLetta = br.readLine();
            			nomeGiocatore		= rigaLetta;
            			actGiocatore		= actSquadra.prelevaGiocatoreDaNome(nomeGiocatore);
            			if (actGiocatore!=null){
            				quantiRigoristi++;
            				actGiocatore.setRigorista(quantiRigoristi+"^");
            			} else {
            				System.err.println(nomeSquadra+" "+nomeGiocatore+" NON TROVATO rig");	
            			}
					}
            	}
    			if (rigaLetta.contains("Calci piazzati")){
    				elaboraCalciPiazzati	= true;
    			}
            	if (elaboraCalciPiazzati){
        			while (elaboraCalciPiazzati) {
        				rigaPrecLetta		= rigaLetta;
            			rigaLetta 			= br.readLine();
            			if (!rigaLetta.contains("Campioncino")){
            				elaboraCalciPiazzati	= false;
            				break;
            			}
        				rigaPrecLetta		=rigaLetta;
        				rigaLetta 			= br.readLine();
        				nomeGiocatore		= rigaLetta;
            			actGiocatore		= actSquadra.prelevaGiocatoreDaNome(nomeGiocatore);
            			if (actGiocatore!=null){
            				quantiCalciPiazzati++;
            				actGiocatore.setCalciPiazzati(quantiCalciPiazzati+"^");
            			} else {
            				System.err.println(nomeSquadra+" "+nomeGiocatore+" NON TROVATO piazz");	
            			}
					}
            	}
				rigaPrecLetta=rigaLetta;
				quantiRigoristi			= 0;
	            quantiCalciPiazzati		= 0;
            }
            br.close();
            */

            String rigaIniSquadra			= "<span class=\"team-name\">";
            int numRigheLette				= 0;
            ArrayList<String> righeLette	= new ArrayList<String>();

            
            String rigaRigorista			= "<header class=\"primary\">Rigori</header>"; 
            String rigaCalci				= "<header>Calci piazzati</header>"; 
            int quantiRigoristi				= 0;
            int quantiCalciPiazzati			= 0;
//            String rigaIniGiocatore			= "<img class=\"player-image\"";
            String rigaIniGiocRigCal			= "alt=\"Campioncino";
            String fineSquadra			= "<img class=\"team-badge\"";
            ///////// RIGORISTI seconda versione
            rigaLetta="";
		    String filePath_rigoristi		= FC_Varie.pathFile+"/"+FC_Varie.nomeFile_rigoristiCPiazzati;
            br 			= new BufferedReader(new FileReader(filePath_rigoristi));
            boolean elaboraRigorista		= false;
            boolean elaboraCalci			= false;
            while ((rigaLetta = br.readLine()) != null) {
            	rigaLetta = rigaLetta.trim();
            	righeLette.add(rigaLetta);
            	numRigheLette++;
            	if (rigaLetta.equals(rigaRigorista)){
            		elaboraRigorista		= true;
            		elaboraCalci			= false;
            		nomeSquadra				= righeLette.get(numRigheLette-6);
            		nomeSquadra				= nomeSquadra.substring(nomeSquadra.indexOf(rigaIniSquadra)+rigaIniSquadra.length());
            		nomeSquadra				= nomeSquadra.substring(0,nomeSquadra.indexOf("<")).toUpperCase(); 
            		actSquadra				= mapSquadre.get(nomeSquadra);
            	}
            	if (elaboraRigorista || elaboraCalci){
        			while (elaboraRigorista) {
            			rigaLetta = br.readLine();
            			rigaLetta = rigaLetta.trim();
            			righeLette.add(rigaLetta);
            			numRigheLette++;
            			if (rigaLetta.contains(rigaCalci)){
            				elaboraRigorista		= false;
            				elaboraCalci			= true;
            				break;
            			}
               			if (rigaLetta.contains(fineSquadra)){
            				elaboraRigorista		= false;
            				elaboraRigorista		= false;
            				break;
            			}
            			if (rigaLetta.contains(rigaIniGiocRigCal)){
                			nomeGiocatore		= rigaLetta.substring(rigaLetta.indexOf(rigaIniGiocRigCal)+rigaIniGiocRigCal.length());
                			nomeGiocatore		= nomeGiocatore.substring(0,nomeGiocatore.indexOf("\"")); 
                			actGiocatore		= actSquadra.prelevaGiocatoreDaNomeNoFascia(nomeGiocatore);
                			//actSquadra.arrPor.get(1).nome.equals(nomeGiocatore)
                			if (actGiocatore!=null){
                				quantiRigoristi++;
                				actGiocatore.setRigorista(quantiRigoristi+"^");
                			}
            			}
        			}
        			quantiRigoristi			= 0;
        			while (elaboraCalci) {
            			rigaLetta = br.readLine();
            			rigaLetta = rigaLetta.trim();
            			righeLette.add(rigaLetta);
            			numRigheLette++;
               			if (rigaLetta.contains(fineSquadra)){
            				elaboraRigorista		= false;
            				elaboraCalci			= false;
            				break;
            			}
            			if (rigaLetta.contains(rigaIniGiocRigCal)){
                			nomeGiocatore		= rigaLetta.substring(rigaLetta.indexOf(rigaIniGiocRigCal)+rigaIniGiocRigCal.length());
                			nomeGiocatore		= nomeGiocatore.substring(0,nomeGiocatore.indexOf("\"")); 
                			actGiocatore		= actSquadra.prelevaGiocatoreDaNomeNoFascia(nomeGiocatore);
                			//actSquadra.arrPor.get(1).nome.equals(nomeGiocatore)
                			if (actGiocatore!=null){
                				quantiCalciPiazzati++;
                				actGiocatore.setCalciPiazzati(quantiCalciPiazzati+"^");
                			}
            			}
        			}
        			quantiCalciPiazzati			= 0;
            	}
            }
            br.close();
            
            ////////////////////////////////////////////
            ////////////// carico INFORTUNATI MEDI E LUNGHI
            String rigaInfortunati	="<strong class=\"label label-primary\">Infortunati</strong>";
            String rigaNessunInfortunato	= "<div class=\"empty-list-message\">Nessuno</div>";
            String rigaIniNomeInfortunato	= "<strong class=\"item-name\">";
            String rigaFineInfortunati		= "</ul>";
		    String filePath_infortunati	= FC_Varie.pathFile+"/"+FC_Varie.nomeFile_infortunati;
            rigaLetta="";
            br 			= new BufferedReader(new FileReader(filePath_infortunati));
            boolean elaboraInfortunati	= false;
            int quantiInfortunati			= 0;
            String dettInfortunio			= "";
            numRigheLette				= 0;
            righeLette	= new ArrayList<String>();
            while ((rigaLetta = br.readLine()) != null) {
            	rigaLetta = rigaLetta.trim();
            	righeLette.add(rigaLetta);
            	numRigheLette++;
            	if (rigaLetta.equals(rigaInfortunati)){
            		elaboraInfortunati		= true;
            		nomeSquadra				= righeLette.get(numRigheLette-7);
            		nomeSquadra				= nomeSquadra.substring(nomeSquadra.indexOf(rigaIniSquadra)+rigaIniSquadra.length());
            		nomeSquadra				= nomeSquadra.substring(0,nomeSquadra.indexOf("<")).toUpperCase(); 
            		actSquadra				= mapSquadre.get(nomeSquadra);
            	}
            	if (elaboraInfortunati){
        			while (elaboraInfortunati) {
            			rigaLetta = br.readLine();
            			rigaLetta = rigaLetta.trim();
            			righeLette.add(rigaLetta);
            			numRigheLette++;
            			if (rigaLetta.equals(rigaNessunInfortunato) || rigaLetta.equals(rigaFineInfortunati)){
            				elaboraInfortunati	= false;
            				break;
            			}
            			if (rigaLetta.contains(rigaIniNomeInfortunato)){
                			nomeGiocatore		= rigaLetta.substring(rigaLetta.indexOf(rigaIniNomeInfortunato)+rigaIniNomeInfortunato.length());
                			nomeGiocatore		= nomeGiocatore.substring(0,nomeGiocatore.indexOf("<")); 
                			actGiocatore		= actSquadra.prelevaGiocatoreDaNomeNoFascia(nomeGiocatore);
                			//actSquadra.arrPor.get(1).nome.equals(nomeGiocatore)
                			if (actGiocatore!=null){
                				quantiInfortunati++;
                    			rigaLetta = br.readLine();
                    			rigaLetta = rigaLetta.trim();
                    			righeLette.add(rigaLetta);
                    			numRigheLette++;
                    			dettInfortunio	= rigaLetta;
                				actGiocatore.setInfortunato(quantiInfortunati+"^");
                    			actGiocatore.setDettInfortunio(dettInfortunio);
                			} else {
                				System.err.println(nomeSquadra+" "+nomeGiocatore+" NON TROVATO inf");
                			}
            			}
					}
            	}
				quantiInfortunati		= 0;
            }
            br.close();
            
            ////////////////////////////
            /////// CARICO DIFENSORI SUDDIVISI PER TOP, SEMITOP, ....

            ////////////////////////////
            /////// CATEGORIE SOS FANTA
            
            ArrayList<String> arrCategorie	= new ArrayList<String>();
            arrCategorie.add(FC_Varie.SEMITOP_ALTI);
            arrCategorie.add(FC_Varie.SEMITOP_BASSI);
            arrCategorie.add(FC_Varie.TOP_ALTI);
            arrCategorie.add(FC_Varie.TOP_BASSI);
            arrCategorie.add(FC_Varie.TOP);
            arrCategorie.add(FC_Varie.SEMITOP);
            arrCategorie.add(FC_Varie.OTTIMI_TITOLARI);
            arrCategorie.add(FC_Varie.BUONI_TITOLARI);
            arrCategorie.add(FC_Varie.SCOMMESSE);
            arrCategorie.add(FC_Varie.JOLLY);
            arrCategorie.add(FC_Varie.TITOLARI);
            arrCategorie.add(FC_Varie.JOLLY_SECONDA_FASCIA);
            arrCategorie.add(FC_Varie.LOW_COST);
            arrCategorie.add(FC_Varie.JOLLY_TERZA_FASCIA);
            arrCategorie.add(FC_Varie.LEGHE_NUMEROSE );
            arrCategorie.add(FC_Varie.GIOVANI);
            arrCategorie.add(FC_Varie.JOLLY_QUARTA_FASCIA);
            arrCategorie.add(FC_Varie.RISCHI);
            arrCategorie.add(FC_Varie.DA_EVITARE);
            arrCategorie.add(FC_Varie.MERCATO);


            String htmlInizioCategoria			= "<div class=\"content\"><p><strong>";
            String fineCategoria			= "</strong>";
//            String fineCategoria1			= " - ";
            String fineCategoria2			= "- ";
            String nomeCategoria			= "";
            String calciatoriInCategoria	= "";
            String[] eleCalciatori			= null;
            
            rigaLetta="";
            boolean elaboraCategorie			= false;
            boolean elaboraCategoria			= true;

            int fileDaLeggere					= 6;

            for (int actFile = 0; actFile < fileDaLeggere; actFile++) {
            	switch (actFile) {
				case 0:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereDif1));
					break;
				case 1:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereDif2));
					break;
				case 2:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereCen1));
					break;
				case 3:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereCen2));
					break;
				case 4:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereAtt1));
					break;
				case 5:
            		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereAtt2));
					break;
				}
                numRigheLette					= 0;
                righeLette						= new ArrayList<String>();
                while ((rigaLetta = br.readLine()) != null && elaboraCategoria) {
                	rigaLetta = rigaLetta.trim();
                	righeLette.add(rigaLetta);
                	numRigheLette++;
//                	if (rigaLetta.contains(htmlInizioCategoria)){
                	nomeCategoria			= FC_Varie.rigaContieneCategoria(rigaLetta,arrCategorie);
                   	if (!FrmStringa.strVoid(nomeCategoria)){
                		elaboraCategorie			= true;
                	}
                	if (elaboraCategorie){
            			while (elaboraCategoria) {
            				rigaLetta				= rigaLetta.substring(rigaLetta.indexOf(htmlInizioCategoria)).trim();
            				nomeCategoria			= rigaLetta.substring(rigaLetta.indexOf(htmlInizioCategoria)+htmlInizioCategoria.length()).trim();
            				nomeCategoria			= nomeCategoria.substring(0,nomeCategoria.indexOf("<")).trim();
            				rigaLetta				= rigaLetta.substring(rigaLetta.indexOf(fineCategoria)+fineCategoria.length()).trim();
            				if (rigaLetta.substring(0,2).equals(fineCategoria2)){
            					rigaLetta				= rigaLetta.substring(2).trim();
            				} else {
            					rigaLetta				= rigaLetta.substring(3).trim();
            				}
            				calciatoriInCategoria	= rigaLetta.substring(0,rigaLetta.indexOf("<"));
            				
            				eleCalciatori			= calciatoriInCategoria.split(",");
            				eleCalciatori			= correggiEleCalciatori(eleCalciatori);
            				for (int actCalciatore = 0; actCalciatore < eleCalciatori.length; actCalciatore++) {
            					nomeGiocatore		= eleCalciatori[actCalciatore].trim();
                    			actGiocatore		= prelevaCalciatoreDaNome(actFile, mapSquadre, nomeGiocatore);
                    			if (actGiocatore!=null){
                    				actGiocatore.setCategoriaAsta(nomeCategoria);
                    			} else {
                    				if (presenteInSerieA(nomeGiocatore)){
                    					System.err.println("-- -- --"+" "+nomeGiocatore+" NON TROVATO cat");
                    				}
                    			}
    						}
                    		if (rigaLetta.indexOf(htmlInizioCategoria)==-1){
                    			elaboraCategoria		= false;
                    			break;
                    		}
    					}
                	}
                }
                br.close();
                elaboraCategoria		= true;
                elaboraCategorie		= false;
			}
            
            /*            
            
            arrCategorie	= new ArrayList<String>();
            arrCategorie.add(FC_Varie.SEMITOP_ALTI+" -");
            arrCategorie.add(FC_Varie.SEMITOP_BASSI+" -");
            arrCategorie.add(FC_Varie.TOP_ALTI+" -");
            arrCategorie.add(FC_Varie.TOP_BASSI+" -");
            arrCategorie.add(FC_Varie.OTTIMI_TITOLARI+" -");
            arrCategorie.add(FC_Varie.BUONI_TITOLARI+" -");
            arrCategorie.add(FC_Varie.SCOMMESSE+" -");
            arrCategorie.add(FC_Varie.JOLLY+" -");
            arrCategorie.add(FC_Varie.TITOLARI+" -");
            arrCategorie.add(FC_Varie.JOLLY_SECONDA_FASCIA+" -");
            arrCategorie.add(FC_Varie.LOW_COST+" -");
            arrCategorie.add(FC_Varie.JOLLY_TERZA_FASCIA+" -");
            arrCategorie.add(FC_Varie.LEGHE_NUMEROSE +" -");
            arrCategorie.add(FC_Varie.GIOVANI+" -");
            arrCategorie.add(FC_Varie.JOLLY_QUARTA_FASCIA+" -");
            arrCategorie.add(FC_Varie.RISCHI+" -");
            arrCategorie.add(FC_Varie.DA_EVITARE+" -");
            arrCategorie.add(FC_Varie.MERCATO+" -");
            
            numRigheLette					= 0;
            righeLette						= new ArrayList<String>();
    		br 								= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_chiPrendereALL));
    		elaboraCategoria				= false;
            while ((rigaLetta = br.readLine()) != null) {
            	rigaLetta = rigaLetta.trim();
            	righeLette.add(rigaLetta);
            	numRigheLette++;
//            	if (rigaLetta.contains(htmlInizioCategoria)){
            	nomeCategoria			= FC_Varie.rigaContieneCategoria(rigaLetta,arrCategorie);
               	if (!FrmStringa.strVoid(nomeCategoria)){
            		elaboraCategoria		= true;
            	}
            	if (elaboraCategoria){
//        				nomeCategoria			= rigaLetta.substring(rigaLetta.indexOf(htmlInizioCategoria)+htmlInizioCategoria.length());
//        				nomeCategoria			= nomeCategoria.substring(0,nomeCategoria.indexOf("<"));
    				calciatoriInCategoria	= rigaLetta.substring(rigaLetta.indexOf(nomeCategoria)+ nomeCategoria.length());
//        				calciatoriInCategoria	= calciatoriInCategoria.substring(0,calciatoriInCategoria.indexOf("<"));
    				
    				eleCalciatori			= calciatoriInCategoria.split(",");
    				eleCalciatori			= correggiEleCalciatori(eleCalciatori);
    				for (int actCalciatore = 0; actCalciatore < eleCalciatori.length; actCalciatore++) {
    					nomeGiocatore		= eleCalciatori[actCalciatore];
            			actGiocatore		= prelevaCalciatoreDaNome(mapSquadre, nomeGiocatore);
            			if (actGiocatore!=null){
            				actGiocatore.setCategoriaAsta(nomeCategoria);
            			} else {
            				System.err.println("-- -- --"+" "+nomeGiocatore+" NON TROVATO cat");
            			}
					}
           			elaboraCategoria		= false;
            	}
            }
            br.close();
            */
            
            
            ////////////////////////////
            /////// SOS ANGOLI E PUNIZIONI
            String htmlInizioSquadra		= "<div class=\"content\"><p><strong>✅ ";
            String htmlIniPunzioni			= "</strong></p></div><div class=\"content\"><p><em>Punizioni</em>: ";
            String htmlIniAngoli			= "<div class=\"content\"><p><em>Corner</em>: ";
            String calciatoriPunizione		= "";
            String calciatoriAngoli			= "";
            eleCalciatori					= null;
            
            rigaLetta="";
            boolean elaboraSquadre			= false;
            boolean elaboraSquadra			= true;

       		br 			= new BufferedReader(new FileReader(FC_Varie.pathFile+"/"+FC_Varie.nomeFile_angoliPunizioni));
            numRigheLette					= 0;
            righeLette						= new ArrayList<String>();
            while ((rigaLetta = br.readLine()) != null && elaboraSquadra) {
            	rigaLetta = rigaLetta.trim();
            	righeLette.add(rigaLetta);
            	numRigheLette++;
            	if (rigaLetta.contains(htmlInizioSquadra)){
            		elaboraSquadre				= true;
            	}
            	if (elaboraSquadre){
        			while (elaboraSquadra) {
        				nomeSquadra				= rigaLetta.substring(rigaLetta.indexOf(htmlInizioSquadra)+htmlInizioSquadra.length());
        				nomeSquadra				= nomeSquadra.substring(0,nomeSquadra.indexOf("<")).toUpperCase();
        				actSquadra				= mapSquadre.get(nomeSquadra);
        				
        				calciatoriPunizione		= rigaLetta.substring(rigaLetta.indexOf(htmlIniPunzioni)+ htmlIniPunzioni.length());
        				calciatoriPunizione		= calciatoriPunizione.substring(0,calciatoriPunizione.indexOf("<"));
        				eleCalciatori			= calciatoriPunizione.split(",");
        				eleCalciatori			= correggiEleCalciatori(eleCalciatori);
        				for (int actCalciatore 	= 0; actCalciatore < eleCalciatori.length; actCalciatore++) {
        					nomeGiocatore		= eleCalciatori[actCalciatore];
        					//System.err.println("cerco: "+nomeGiocatore);
                			actGiocatore		= actSquadra.prelevaGiocatoreDaNomeNoFascia(nomeGiocatore);
                			if (actGiocatore!=null){
            					//System.err.println("trovato: "+nomeSquadra+" "+actGiocatore.id);
                				actGiocatore.setPunizioni((actCalciatore+1)+"^");
                			} else {
                		    	System.err.println(nomeSquadra+" "+nomeGiocatore+" NON TROVATO pun");
                			}
						}
        				
        				// avanzo con la lettura
        				rigaLetta				= rigaLetta.substring(rigaLetta.indexOf(htmlIniPunzioni)+ htmlIniPunzioni.length());
        				
        				calciatoriAngoli		= rigaLetta.substring(rigaLetta.indexOf(htmlIniAngoli)+ htmlIniAngoli.length());
        				calciatoriAngoli		= calciatoriAngoli.substring(0,calciatoriAngoli.indexOf("<"));
        				eleCalciatori			= calciatoriAngoli.split(",");
        				eleCalciatori			= correggiEleCalciatori(eleCalciatori);
        				for (int actCalciatore	= 0; actCalciatore < eleCalciatori.length; actCalciatore++) {
        					nomeGiocatore		= eleCalciatori[actCalciatore];
//        					System.err.println("cerco: "+eleCalciatori[actCalciatore]);
                			actGiocatore		= actSquadra.prelevaGiocatoreDaNomeNoFascia(nomeGiocatore);
                			if (actGiocatore!=null){
//            					System.err.println("trovato: "+nomeSquadra+" "+actGiocatore.id);
                				actGiocatore.setAngoli((actCalciatore+1)+"^");
                			} else {
                				System.err.println(nomeSquadra+" "+nomeGiocatore+" NON TROVATO ang");
                			}
						}

        				// avanzo con la lettura
        				rigaLetta				= rigaLetta.substring(rigaLetta.indexOf(htmlIniAngoli)+ htmlIniAngoli.length());

                		if (rigaLetta.indexOf(htmlInizioSquadra)==-1){
                			elaboraSquadra		= false;
                			break;
                		}
					}
            	}
            }
            br.close();
            
            
		    /////////////// sql x database
		    
		    if (true){
		    
		    StringBuffer sbSqlInsert	= new StringBuffer();
		    int progrRuolo				= 1;
		    int progrGiocatore			= 1;
		    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
		    	nomeSquadra				= entry.getKey();
	            actSquadra				= entry.getValue();
	            progrGiocatore			= 1;
            	progrRuolo				= 1;
	            for (int i = 0; i < actSquadra.arrPor.size(); i++) {
	            	actPortiere			= actSquadra.arrPor.get(i);
	            	actGiocatore		= actPortiere;
	            	addComandiSql(sbSqlInsert, nomeSquadra, actGiocatore, progrGiocatore, progrRuolo);
	            	progrGiocatore++;
	            	progrRuolo++;
				}
            	progrRuolo				= 1;
	            for (int i = 0; i < actSquadra.arrDif.size(); i++) {
	            	actDifensore		= actSquadra.arrDif.get(i);
	            	actGiocatore		= actDifensore;
	            	addComandiSql(sbSqlInsert, nomeSquadra, actGiocatore, progrGiocatore, progrRuolo);
	            	progrGiocatore++;
	            	progrRuolo++;
				}
            	progrRuolo				= 1;
	            for (int i = 0; i < actSquadra.arrCen.size(); i++) {
	            	actCentrocampista	= actSquadra.arrCen.get(i);
	            	actGiocatore		= actCentrocampista;
	            	addComandiSql(sbSqlInsert, nomeSquadra, actGiocatore, progrGiocatore, progrRuolo);
	            	progrGiocatore++;
	            	progrRuolo++;
				}
            	progrRuolo				= 1;
	            for (int i = 0; i < actSquadra.arrAtt.size(); i++) {
	            	actAttaccante		= actSquadra.arrAtt.get(i);
	            	actGiocatore		= actAttaccante;
	            	addComandiSql(sbSqlInsert, nomeSquadra, actGiocatore, progrGiocatore, progrRuolo);
	            	progrGiocatore++;
	            	progrRuolo++;
				}
            	
	        }
		    System.out.println("INSERT PER DATABASE: \n\n "+sbSqlInsert.toString());
		    
		    }
		    
		    ////////////////////////////////////////////////////////////
		    ///////////////////// elenco dei giocatori difensivi Esterni
		    
		    /*
		    StringBuffer sbEsterniDif	= new StringBuffer();
		    TreeMap<String, ArrayList<Difensore>> mapDifEsterni	 =  new TreeMap<String, ArrayList<Difensore>>();
		    ArrayList<Difensore> arrDifEsterni	 =  new ArrayList<Difensore>();
		    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
		    	nomeSquadra				= entry.getKey();
	            actSquadra				= entry.getValue();
	            for (int i = 0; i < actSquadra.arrDif.size(); i++) {
	            	actDifensore		= actSquadra.arrDif.get(i);
	            	if (actDifensore.isEsterno()){
	            		arrDifEsterni	= mapDifEsterni.get(nomeSquadra);
	            		if (arrDifEsterni==null){
	            			arrDifEsterni	 =  new ArrayList<Difensore>();
		            		mapDifEsterni.put(nomeSquadra, arrDifEsterni);
	            		}
	            		arrDifEsterni.add(actDifensore);
	            	}
				}
		    }
		    
		    
			System.out.println("\n\n ----- DIFENSORI ESTERNI ----- \n");
		    for (Entry<String, ArrayList<Difensore>> entry : mapDifEsterni.entrySet()) {
		    	nomeSquadra			= entry.getKey();
				arrDifEsterni		= entry.getValue();
				System.out.println("\n"+nomeSquadra + ": ");
				for (int i = 0; i < arrDifEsterni.size(); i++) {
					actDifensore	= arrDifEsterni.get(i);	
					System.out.println("\t "+ actDifensore.getQuotazioneAtt()+" "+actDifensore.getCategoriaAsta()+" "+actDifensore.nome+" "+actDifensore.getRigorista()+" "+actDifensore.getCalciPiazzati()+" "+actDifensore.getInfortunato());
				}
			}
			*/

		    ///////////  intestazione
		    StringBuffer sbIntestazione = new StringBuffer();
		    sbIntestazione
			.append("id")
			.append("; Nome")
			.append("; Offesivo")
			.append("; Ruoli")
			.append("; Squadra")
			.append("; A_C_D")
			.append("; Quotazione Att")
			.append("; Quotazione Ini")
			.append("; Differenza")
			.append("; Categoria Asta")
			.append("; Rigorista")
			.append("; Calci Piazzati")
			.append("; Punizioni")
			.append("; Angoli")
			.append("; ATT_Gol; ATT_Ass; ATT_PG; ATT_FM; ATT_M; ATT_Amm; ATT_Esp; ATT_RigT; ATT_RigS")
			.append("; LAST_Gol; LAST_Ass; LAST_PG; LAST_FM; LAST_M; LAST_Amm; LAST_Esp; LAST_RigT; LAST_RigS")
			.append("; STO_anni; STO_totGol; STO_totAss; STO_PG; STO_mediaFM; STO_mediaM")
			.append("; Infortunato")
			.append("; DettInfortunio")
			;
		    System.out.println(sbIntestazione);
		    
		    ////////////////////////////////////////////////////////////
		    ///////////////////// elenco TUTTI DIFENSORI catalogati per ruolo 
		    TreeMap<String, ArrayList<Difensore>> mapDif	 =  new TreeMap<String, ArrayList<Difensore>>();
		    ArrayList<Difensore> arrDif	 =  new ArrayList<Difensore>();
		    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
		    	nomeSquadra				= entry.getKey();
	            actSquadra				= entry.getValue();
	            for (int i = 0; i < actSquadra.arrDif.size(); i++) {
	            	actDifensore	= actSquadra.arrDif.get(i);
            		arrDif	= mapDif.get(nomeSquadra);
            		if (arrDif==null){
            			arrDif =  new ArrayList<Difensore>();
            			mapDif.put(nomeSquadra, arrDif);
            		}
            		arrDif.add(actDifensore);
				}
		    }

//			System.out.println("\n\n ----- DIFENSORI -----  \n");
		    for (Entry<String, ArrayList<Difensore>> entry : mapDif.entrySet()) {
		    	nomeSquadra			= entry.getKey();
				arrDif				= entry.getValue();
//				System.out.println("\n"+nomeSquadra + ": ");
				for (int i = 0; i < arrDif.size(); i++) {
					actDifensore	= arrDif.get(i);	
					System.out.println(actDifensore.prelevaScheda());
				}
			}

		    ////////////////////////////////////////////////////////////
		    ///////////////////// elenco TUTTI CENTROCAMPISTI catalogati per ruolo 

		    TreeMap<String, ArrayList<Centrocampista>> mapCenOffensivi	 =  new TreeMap<String, ArrayList<Centrocampista>>();
		    ArrayList<Centrocampista> arrCenOffensivi	 =  new ArrayList<Centrocampista>();
		    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
		    	nomeSquadra				= entry.getKey();
	            actSquadra				= entry.getValue();
	            for (int i = 0; i < actSquadra.arrCen.size(); i++) {
	            	actCentrocampista	= actSquadra.arrCen.get(i);
            		arrCenOffensivi	= mapCenOffensivi.get(nomeSquadra);
            		if (arrCenOffensivi==null){
            			arrCenOffensivi =  new ArrayList<Centrocampista>();
            			mapCenOffensivi.put(nomeSquadra, arrCenOffensivi);
            		}
            		arrCenOffensivi.add(actCentrocampista);
				}
		    }

//			System.out.println("\n\n ----- CENTROCAMPISTI -----  \n");
		    for (Entry<String, ArrayList<Centrocampista>> entry : mapCenOffensivi.entrySet()) {
		    	nomeSquadra			= entry.getKey();
				arrCenOffensivi		= entry.getValue();
//				System.out.println("\n"+nomeSquadra + ": ");
				for (int i = 0; i < arrCenOffensivi.size(); i++) {
					actCentrocampista	= arrCenOffensivi.get(i);	
					System.out.println(actCentrocampista.prelevaScheda());
				}
			}
		    

		    ////////////////////////////////////////////////////////////
		    ///////////////////// elenco TUTTI ATTACCANTI catalogati per ruolo 

		    TreeMap<String, ArrayList<Attaccante>> mapAtt	=  new TreeMap<String, ArrayList<Attaccante>>();
		    ArrayList<Attaccante> arrAtt					=  new ArrayList<Attaccante>();
		    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
		    	nomeSquadra				= entry.getKey();
	            actSquadra				= entry.getValue();
	            for (int i = 0; i < actSquadra.arrAtt.size(); i++) {
	            	actAttaccante	= actSquadra.arrAtt.get(i);
            		arrAtt			= mapAtt.get(nomeSquadra);
            		if (arrAtt==null){
            			arrAtt		=  new ArrayList<Attaccante>();
            			mapAtt.put(nomeSquadra, arrAtt);
            		}
            		arrAtt.add(actAttaccante);
				}
		    }


//			System.out.println("\n\n ----- ATTACCANTI -----  \n");
		    for (Entry<String, ArrayList<Attaccante>> entry : mapAtt.entrySet()) {
		    	nomeSquadra			= entry.getKey();
				arrAtt				= entry.getValue();
//				System.out.println("\n"+nomeSquadra + ": ");
				for (int i = 0; i < arrAtt.size(); i++) {
					actAttaccante	= arrAtt.get(i);	
					System.out.println(actAttaccante.prelevaScheda());
				}
			}
		    
		    
		    

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String[] correggiEleCalciatori(String[] eleCalciatori){
//		boolean esisteBaco			= false;
		String nomeGiocatore		= "";
		ArrayList<String> newArr	= new ArrayList<String>();
		String[] eleCalciatoriTm	= null;
		for (int actCalciatore 	= 0; actCalciatore < eleCalciatori.length; actCalciatore++) {
			nomeGiocatore			= eleCalciatori[actCalciatore];
			if (nomeGiocatore.indexOf(".")!=-1 && !eccezioni_nomiConPunto(nomeGiocatore)){
				eleCalciatoriTm		= nomeGiocatore.split("\\.");
				for (int j	= 0; j< eleCalciatoriTm.length; j++) {
					newArr.add(eleCalciatoriTm[j].trim());
				}
//				esisteBaco			= false;
//				break;
			} else if (nomeGiocatore.indexOf("/")!=-1){
				eleCalciatoriTm		= nomeGiocatore.split("/");
				for (int j	= 0; j< eleCalciatoriTm.length; j++) {
					newArr.add(eleCalciatoriTm[j].trim());
				}
			} else {
				newArr.add(nomeGiocatore);
			}
		}
		eleCalciatori				= newArr.toArray(new String[newArr.size()]);
		return eleCalciatori;
	}
	
	private static boolean eccezioni_nomiConPunto (String nome){
		nome				= nome.trim().toLowerCase();
		boolean eccezione	= false;
		if (nome.equals(FC_Varie.ECC_DIF_Esteves_UDI.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_W__Coulibaly.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_L__Coulibaly.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_A__Carboni.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_CEN_K__Thuram_JUV.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_CEN_S__Vignato_MON.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_I__Touré.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_M__Moreno.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_A__Moreno.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_DIF_F__Carboni.toLowerCase())){
			eccezione		= true;
		} else if (nome.equals(FC_Varie.ECC_CEN_M__Koné.toLowerCase())){
			eccezione		= true;
		}
		return eccezione;
	}
	
	private static Giocatore prelevaCalciatoreDaNome(int actFile, Map<String, Squadra> mapSquadre, String nomeGiocatore){
		Giocatore actGiocatore	= null;
		Squadra actSquadra		= null;
	    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
	    	actSquadra			= entry.getValue();
	    	actGiocatore		= actSquadra.prelevaGiocatoreDaNome(actFile, nomeGiocatore);
	    	if (actGiocatore!=null){
	    		break;
	    	}
	    }
		return actGiocatore;
	}
	
	
	private static Giocatore prelevaCalciatoreDaID(Map<String, Squadra> mapSquadre, int idGiocatore){
		Giocatore actGiocatore	= null;
		Squadra actSquadra		= null;
	    for (Entry<String, Squadra> entry : mapSquadre.entrySet()) {
	    	actSquadra			= entry.getValue();
	    	actGiocatore		= actSquadra.prelevaGiocatoreDaID(idGiocatore);
	    	if (actGiocatore!=null){
	    		break;
	    	}
	    }
		return actGiocatore;
	}
	
	private static void addComandiSql(StringBuffer sbSqlInsert, String nomeSquadra,
			Giocatore actGiocatore, int progrGiocatore, int progrRuolo){
		
//		RuoloDett	QuotAtt	QuotIni	CatAsta	RigDes	CalPiaz	PunDes	AngDes	ATT_Gol	ATT_Ass	ATT_PG	ATT_FM	ATT_M	ATT_Amm	ATT_Esp	ATT_RigT ATT_RigS
		
		Statistica statATT	= actGiocatore.getMapStatistiche().get(2024);
		if (statATT==null){
			statATT			= new  Statistica();
		}
//		Statistica statLAST	= actGiocatore.getMapStatistiche().get(2023);
		
    	sbSqlInsert.append("")
    	.append("\n")
    	.append("INSERT INTO Giocatori (" +
    			"Id, Nome, " +
    			"Ruolo, RuoloDett, " +
    			"QuotAtt, QuotIni, " +
    			"CatAsta, RigDes, CalPiaz, " +
    			"PunDes, AngDes, " +
    			"ATT_Gol, ATT_Ass, ATT_PG, ATT_FM, ATT_M, " +
    			"ATT_Amm, ATT_Esp, ATT_RigT, ATT_RigS" +
    			")")
    	.append(" VALUES (" +
    			"'"+actGiocatore.id+"', '"+formatSql(actGiocatore.nome)+"'" +
    			",'"+actGiocatore.ruoloACDP+"','"+actGiocatore.quantoOffesivo()+"'" +
    			","+actGiocatore.getQuotazioneAtt()+","+actGiocatore.getQuotazioneIni()+"" +
    			",'"+actGiocatore.getCategoriaAsta()+"','"+actGiocatore.getRigorista()+"','"+actGiocatore.getCalciPiazzati()+"'" +
    			",'"+actGiocatore.getPunizioni()+"','"+actGiocatore.getAngoli()+"'" +
    			","+statATT.getGolFatti()+","+statATT.getAssist()+","+statATT.getPartiteGiocate()+","+statATT.getFantamediaVoto()+","+statATT.getMediaVoto()+
    			","+statATT.getAmmonizioni()+","+statATT.getEspulsioni()+","+statATT.getRigoriTirati()+","+statATT.getRigoriSbagliati()+
    			");")
    	;
    	sbSqlInsert.append("")
    	.append("\n")
    	.append("INSERT INTO Squadre (NomeSquadra, ProgrGiocatore, Ruolo, ProgrRuolo, IdGiocatore)")
    	.append(" VALUES ('"+nomeSquadra+"', '"+progrGiocatore+"', '"+actGiocatore.ruoloACDP+"', '"+progrRuolo+"', '"+actGiocatore.id+"');")
    	;
	}

	private static String formatSql (String nome){
		return nome.replaceAll("'", "''");
	}
	
	static boolean presenteInSerieA(String actNome){
		boolean presente	= true;
		if (actNome.equals("Joao Ferreira")
				|| actNome.equals("G. Esteves")
				|| actNome.equals("Lella")){
			presente		= false;
		}
		return presente;
	}
	
}

