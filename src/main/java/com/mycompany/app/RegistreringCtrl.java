package com.mycompany.app;

import java.sql.SQLException;

public class RegistreringCtrl extends DBConnect {

  private Apparat apparat;
  private Ovelse ovelse;
  private Treningsokt treningsokt;

  public RegistreringCtrl() {
    connect();
    // La laging av avtale vaere en transaksjon
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println("db error during setAuoCommit of RegistreringCtrl=" + e);
      return;
    }
  }

  // Use case 1
  public Apparat registrerApparat(int apparatId, String navn, String beskrivelse) throws SQLException {
    // Sjekk om apparat allerede eksisterer
    apparat = new Apparat(apparatId);
    apparat.initialize(conn);
    if (apparat.getApparatId() == null) {
      //Hvis ikke, lag en ny rad i apparat tabellen
      apparat = new Apparat(apparatId, navn, beskrivelse);
      apparat.save(conn);
    } else {
    		throw new SQLException("Apparat already exist!");
    }
    return apparat;
  }

  // Use case 1
  public OvelsePaApparat registrerOvelsePaApparat(
      int ovelseId, String navn, int antallKilo, int antallSett, int apparatId) throws SQLException {
    Apparat app = new Apparat(apparatId);
    app.initialize(conn);
    OvelsePaApparat ovelsePaApparat = new OvelsePaApparat(ovelseId);
    ovelsePaApparat.initialize(conn);
    if (ovelsePaApparat.getOvelseId() == null) {
    		ovelsePaApparat = new OvelsePaApparat(ovelseId, navn, antallKilo, antallSett, app);
    		ovelsePaApparat.save(conn);
    } else {
    		throw new SQLException("Ovelse already exist!");
    }
    return ovelsePaApparat;
  }

  // Use case 1
  public OvelseUtenApparat registrerOvelseUtenApparat(int ovelseId, String navn, String beskrivelse) {
	    OvelseUtenApparat ovelseUtenApparat = new OvelseUtenApparat(ovelseId, navn, beskrivelse);
	    ovelseUtenApparat.save(conn);
	    return ovelseUtenApparat;
  }
  
  public Notat registrerNotat(int notatId, String beskrivelse) {
	    // Sjekk om apparat allerede eksisterer
	    Notat notat = new Notat(notatId, beskrivelse);
	    notat.initialize(conn);
	    if (notat.getNotatId() == null) {
	      //Hvis ikke, lag en ny rad i apparat tabellen
	    	notat = new Notat(notatId, beskrivelse);
	    	notat.save(conn);
	    }
	    return notat;
	  }

  // Use case 1
  public Treningsokt registrerTreningsokt(
	      int oktId, String dato, String tidspunkt, int varighet, int form, int prestasjon, int notatId, String beskrivelse) {
	  registrerNotat(notatId, beskrivelse);
	  Treningsokt treningsokt = new Treningsokt(oktId, dato, tidspunkt, varighet, form, prestasjon);
	  treningsokt.save(conn);
	  return treningsokt;
	  
  }

  public void fullforRegistrering() {
    // Skriv inn alt som skal lagres
    try {
      conn.commit();
    } catch (SQLException e) {
      System.out.println("db error during commit of RegistreringCtrl=" + e);
      return;
    }
  }
}