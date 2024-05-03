import enums.OpzioniBatman;
import enums.OpzioniCliente;
import enums.OpzioniManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleManage {
    private static int indexMenu = 0;
    private Scanner myScan = new Scanner(System.in);
    private String sIntRegex = "[0-9]+";
    private String sMailRegex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";
    private String sYearRegex = "\\d{4}";
    private String sMonthRegex = "(0[1-9]|1[012])";
    private String sDayRegex = "(0[1-9]|[12][1-9]|3[01])";
    private String sCfRegex = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
    private String sHourRegex = "([01]?[0-9]|2[0-3])";
    private String sMinuteRegex = "[0-5][0-9]";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public ConsoleManage() {
    }

    // RESTITUISCE UN NUMERO SE INSERITO ENTRO TOT TENTATIVI
    public int[] giveInt(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        int[] rNumber = new int[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String number = myScan.nextLine();
            if (!number.matches(sIntRegex)) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rNumber[0] = 0;
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rNumber[0] = 1;
                rNumber[1] = Integer.parseInt(number);
                break;
            }
        }
        return rNumber;
    }
//	ESEMPIO DI UTILIZZO DI giveInt()
//	ConsoleManage cm = new ConsoleManage();
//	int[] cNumbers = new int[2];
//	cNumbers = (cm.giveInt("Inserisci un numero", "Non è stato riconosciuto come numero",
//			"Non è stao inserito un numero", 3));
//	if (cNumbers[0] == 1) {
//		int number = cNumbers[1];
//		System.out.println("il numero inserito è: " + number);
//	}

    // RESTITUISCE UN INDIRIZZO MAIL SE INSERITO ENTRO TOT TENTATIVI
    public String[] giveMail(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        String[] rMail = new String[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sMail = myScan.nextLine();
            if (!sMail.toUpperCase().matches(sMailRegex)) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rMail[0] = "0";
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rMail[0] = "1";
                rMail[1] = sMail.toLowerCase();
                break;
            }
        }
        return rMail;
    }

//	ESEMPIO DI UTILIZZO
//	ConsoleManage cm = new ConsoleManage();
//	String[] cMail = new String[2];
//	cMail = (cm.giveMail("Inserisci un indirizzo Email", "Non è stato riconosciuto come un indirizzo Email",
//			"Non è stao inserito un indirizzo Email", 3));
//	if (cMail[0] == "1") {
//		String mail = cMail[1];
//		System.out.println("la mail inserita è: " + mail);
//	}

    // RESTITUISCE UNA DATA SE INSERITA ENTRO TOT TENTATIVI
    public LocalDate[] giveDate(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        LocalDate[] rDate = new LocalDate[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sDate = myScan.nextLine();
            String splitDate[] = sDate.split("/");
            if (splitDate.length == 3) {
                if (!splitDate[0].matches(sDayRegex) || !splitDate[1].matches(sMonthRegex)
                        || !splitDate[2].matches(sYearRegex)) {
                    n++;
                    if (n == iNumTry) {
                        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                        rDate[0] = LocalDate.of(0001, 01, 01);
                        break;
                    }
                    System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
                } else {
                    try {
                        rDate[0] = LocalDate.of(0002, 01, 01);
                        rDate[1] = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),
                                Integer.parseInt(splitDate[0]));
                        break;
                    } catch (Exception e) {
                        n++;
                        if (n == iNumTry) {
                            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                            rDate[0] = LocalDate.of(0001, 01, 01);
                            break;
                        }
                        System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
                    }

                }
            } else if (splitDate.length != 3) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rDate[0] = LocalDate.of(0001, 01, 01);
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            }
        }
        return rDate;
    }
// ESEMPIO DI UTILIZZO
//	ConsoleManage cm = new ConsoleManage();
//	LocalDate[] cDate = new LocalDate[2];
//	cDate = (cm.giveDate("Inserisci una data (dd/mm/yyyy)", "Non è stata riconosciuta come data",
//			"Non è stata inserita una data", 3));
//	if (cDate[0].equals(LocalDate.of(0002, 01, 01))) {
//		LocalDate date = cDate[1];
//		System.out.println("la data inserita è: " + date);
//	}

    // RESTITUISCE UN CODICE FISCALE SE INSERITO ENTRO TOT TENTATIVI
    public String[] giveCf(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        String[] rCf = new String[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sCf = myScan.nextLine();
            if (!sCf.toUpperCase().matches(sCfRegex)) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rCf[0] = "0";
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rCf[0] = "1";
                rCf[1] = sCf.toUpperCase();
                break;
            }
        }
        return rCf;
    }
// ESEMPIO DI UTILIZZO
//	ConsoleManage cm = new ConsoleManage();
//	String[] cCf = new String[2];
//	cCf = (cm.giveCf("Inserisci un codice fiscale", "Non è stato riconosciuto come codice fiscale",
//			"Non è stato inserito un codice fiscale", 3));
//	if (cCf[0].equals("1")) {
//		String cf = cCf[1];
//		System.out.println("Il codice fiscale inserito è: " + cf);
//	}

    // RESTITUISCE UNA STRINGA SE INSERITA ENTRO TOT TENTATIVI
    public String[] giveString(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        String[] rString = new String[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sString = myScan.nextLine();
            if (sString.equals("")) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rString[0] = "0";
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rString[0] = "1";
                rString[1] = sString;
                break;
            }
        }
        return rString;
    }

    //   ESEMPIO DI UTILIZZO
//	ConsoleManage cm = new ConsoleManage();
//	String[] cString = new String[2];
//	cString = (cm.giveString("Inserisci una stringa", "Non è stata riconosciuta come stringa",
//			"Non è stata inserita una stringa", 3));
//	if (cString[0].equals("1")) {
//		String string = cString[1];
//		System.out.println("La sringa inserita è: " + string);
//	}
    // RESTITUISCE UN SESSO ANAGRAFICO SE INSERITO TRA M ED F ENTRO TOT TENTATIVI
    public String[] giveSex(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        String[] rSex = new String[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sSex = myScan.nextLine();
            if (!sSex.toUpperCase().equals("M") && !sSex.toUpperCase().equals("F")) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rSex[0] = "0";
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rSex[0] = "1";
                rSex[1] = sSex.toUpperCase();
                break;
            }
        }
        return rSex;
    }
//  ESEMPIO DI UTILIZZO
//	ConsoleManage cm = new ConsoleManage();
//	String[] cSex = new String[2];
//	cSex = (cm.giveSex("Inserisci un sesso anagrafico (M/F)", "Non è stato riconosciuto come sesso anagrafico",
//			"Non è stato inserito un sesso anagrafico", 3));
//	if (cSex[0].equals("1")) {
//		String sex = cSex[1];
//		System.out.println("Il sesso anagrafico inserito è: " + sex);
//	}


    // ESEMPIO DI UTILZZO


    // RESTITUISCE UNA LETTERA TRA QUELLE INDICATE SE INSERITA ENTRO TOT TENTATIVI
    public String[] giveMenuOption(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry, String validAnswers) {
        int n = 0;
        String[] rMenu = new String[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sMenu = myScan.nextLine();
            if (sMenu.length() != 1 || !validAnswers.toUpperCase().contains(sMenu.toUpperCase())) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rMenu[0] = "0";
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            } else {
                rMenu[0] = "1";
                rMenu[1] = sMenu.toUpperCase();
                break;
            }
        }
        return rMenu;
    }
    // ESEMPIO DI UTILZZO
//	ConsoleManage cm = new ConsoleManage();
//	String[] cMenu = new String[2];
//	cMenu = (cm.giveMenuOption("Cosa desideri fare? \nA)Opzione 1\nB)Opzione2\nC)Opzione 3\nD)Esci", "Non è stata riconosciuta come risposta valida",
//			"Non è stata inserita una risposta valida", 3, "ABCD"));
//	if (cMenu[0].equals("1")) {
//		String menu = cMenu[1];
//		System.out.println("La risposta inserita è: " + menu);
//	}


    // RESTITUISCE UN ORARIO SE INSERITO ENTRO TOT TENTATIVI
    public LocalTime[] giveTime(String sRequestMsg, String sRetryMsg, String errorMsg, int iNumTry) {
        int n = 0;
        LocalTime[] rTime = new LocalTime[2];
        while (true) {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + sRequestMsg + ANSI_RESET);
            String sTime = myScan.nextLine();
            String splitTime[] = sTime.split(":");
            if (splitTime.length == 2) {
                if (!splitTime[0].matches(sHourRegex) || !splitTime[1].matches(sMinuteRegex)) {
                    n++;
                    if (n == iNumTry) {
                        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                        rTime[0] = LocalTime.of(00, 00);
                        break;
                    }
                    System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
                } else {
                    rTime[0] = LocalTime.of(00, 01);
                    rTime[1] = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
                    break;
                }
            } else if (splitTime.length != 2) {
                n++;
                if (n == iNumTry) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + errorMsg + ANSI_RESET);
                    rTime[0] = LocalTime.of(00, 00);
                    break;
                }
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + sRetryMsg + ANSI_RESET);
            }
        }
        return rTime;
    }
    // ESEMPIO DI UTILIZZO
//		ConsoleManage cm = new ConsoleManage();
//		LocalTime[] cTime = new LocalTime[2];
//		cTime = (cm.giveTime("Inserisci un orario (hh:mm)", "Non è stato riscontrato come orario",
//				"Non è stato inserito un orario", 3));
//		if (cTime[0].equals(LocalTime.of(00,01))) {
//			LocalTime time = cTime[1];
//			System.out.println("L'orario inserito è: " + time);
//		}

    public String dammiTarga(String msgShow, String msgRetry, String msgError,
                             String msgSuccess, int tentativi) {
        String input = null;
        String regexCodiceFiscale = "String regex = \"^[A-Z]{2}\\\\d{3}[A-Z]{2}$\";";

        do {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + msgShow + ANSI_RESET);
            input = myScan.nextLine().trim();
            if (!Pattern.matches(regexCodiceFiscale, input)) {
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgRetry + ANSI_RESET);
                tentativi--;
            }
        } while (input == null || !Pattern.matches(regexCodiceFiscale, input) && tentativi != 0);


        if (input == null) {
            System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgError + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return input;
    }

    public Double dammiDouble(String msgShow, String msgRetry, String msgError,
                              String msgSuccess, int tentativi) {
        String input;
        Double result = null;
        String regexDouble = "^\\d*\\.?\\d+$";
        do {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + msgShow + ANSI_RESET);
            input = myScan.nextLine().trim();
            if (!input.isEmpty() && !Pattern.matches(regexDouble, input)) {
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgRetry + ANSI_RESET);
                tentativi--;
            }
        } while ((input.isEmpty() || !Pattern.matches(regexDouble, input)) && tentativi != 0);
        if (tentativi == 0) {
            System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgError + ANSI_RESET);
        } else {
            result = Double.parseDouble(input);
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return result;
    }

    public String dammiPassword(String msgShow, String msgRetry, String msgError,
                                String msgSuccess, int tentativi) {
        String input = null;
        String regexString = "^(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,}$";
        do {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + msgShow + ANSI_RESET);
            input = myScan.nextLine().trim();
            if (!input.isEmpty() && !Pattern.matches(regexString, input)) {
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgRetry + ANSI_RESET);
                tentativi--;
            }
        } while (input.isEmpty() || (!input.isEmpty() && !Pattern.matches(regexString, input)) && tentativi != 0);
        if (tentativi == 0) {
            System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + msgError + ANSI_RESET);
            input = null;
        } else {
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return input;
    }

    public Integer registrazioneLogin() {
        Integer scelta = null;
            System.out.println("Sei registrato?\n1 -> registrazione\n2 -> login\n0 ->esci");
            scelta = Integer.parseInt(myScan.nextLine());
        return scelta;
    }

    public OpzioniManager stampaOpzioniManager() {
        int input;
        do {
            for (OpzioniManager opzione : OpzioniManager.values()) {
                System.out.println(++indexMenu + " -> " + opzione.getDescription());
            }
            System.out.println("Inserisci la tua scelta: ");
            input = Integer.parseInt(myScan.nextLine());
            if (input < 0 || input >= OpzioniManager.values().length) {
                System.out.println("Scelta non valida. Riprova.");
            }
        } while (input < 0 || input >= OpzioniManager.values().length);
        System.out.println("Hai scelto: " + OpzioniManager.values()[input-1]);
        return OpzioniManager.values()[input-1];
    }

    public OpzioniCliente stampaOpzioniCliente() {
        int input;
        do {
            for (OpzioniCliente opzione : OpzioniCliente.values()) {
                System.out.println(++indexMenu + " -> " + opzione.getDescription());
            }
            System.out.println("Inserisci la tua scelta: ");
            input = Integer.parseInt(myScan.nextLine());
            if (input < 0 || input >= OpzioniCliente.values().length) {
                System.out.println("Scelta non valida. Riprova.");
            }
        } while (input < 0 || input >= OpzioniCliente.values().length);
        System.out.println("Hai scelto: " + OpzioniCliente.values()[input-1]);
        return OpzioniCliente.values()[input-1];
    }

    public OpzioniBatman stampaOpzioniBatman() {

            int input;
            do {
                for (OpzioniBatman opzione : OpzioniBatman.values()) {
                    System.out.println(++indexMenu + " -> " + opzione.getDescription());
                }
                System.out.println("Inserisci la tua scelta: ");
                input = Integer.parseInt(myScan.nextLine());
                if (input < 0 || input >= OpzioniBatman.values().length) {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } while (input < 0 || input >= OpzioniBatman.values().length);
            System.out.println("Hai scelto: " + OpzioniBatman.values()[input-1]);
            return OpzioniBatman.values()[input-1];
        }


    public void closeScanner(){
        myScan.close();
    }
}