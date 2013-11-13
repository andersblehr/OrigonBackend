package com.origoapp.api.strings;


public class OStrings
{
    public static final String LANG_DANISH = "da";
    public static final String LANG_ENGLISH = "en";
    public static final String LANG_NORWEGIAN_BOKMAL = "nb";
    public static final String LANG_SWEDISH = "sv";
    
    
    public OStrings()
    {
        this(LANG_ENGLISH);
    }
    
    
    public OStrings(String language)
    {
        if (language.equals(LANG_DANISH) || language.equals(LANG_SWEDISH)) {
            language = LANG_NORWEGIAN_BOKMAL;
        }
        
        setCrossViewTermsAndStrings(language);
        setDefaultStrings(language);
        
        setAuthViewStrings(language);
        setOrigoListViewStrings(language);
        setOrigoViewStrings(language);
        setMemberViewStrings(language);
        
        setCalendarViewStrings(language);
        setTaskViewStrings(language);
        setMessageBoardViewStrings(language);
        setSettingListViewStrings(language);
        setSettingViewStrings(language);
        
        setOrigoTypeStrings(language);
        setMeta(language);
        setLanguageStrings(language);
    }
    
    
    /* ==== Cross-view terms & strings ==== */
    
    public String strFooterTapToEdit;
    public String strFooterOrigoSignature;
    
    public String strButtonOK;
    public String strButtonEdit;
    public String strButtonNext;
    public String strButtonDone;
    public String strButtonContinue;
    public String strButtonCancel;
    public String strButtonSignOut;
    
    public String strAlertTextNoInternet;
    public String strAlertTextServerError;
    public String strAlertTextLocating;
    
    public String strTermYes;
    public String strTermNo;
    public String strTermMan;
    public String strTermBoy;
    public String strTermWoman;
    public String strTermGirl;
    
    public String strFormatAge;
    
    public String strSeparatorAnd;
    
    private void setCrossViewTermsAndStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strFooterTapToEdit      = "Berør teksten for å gjøre endringer.";
            strFooterOrigoSignature = "\n\nSendt fra Origo - http://origoapp.com";
            
            strButtonOK             = "OK";
            strButtonEdit           = "Rediger";
            strButtonNext           = "Neste";
            strButtonDone           = "Ferdig";
            strButtonContinue       = "Fortsett";
            strButtonCancel         = "Avbryt";
            strButtonSignOut        = "Logg ut";
            
            strAlertTextNoInternet  = "Ingen internettforbindelse.";
            strAlertTextServerError = "Det har oppstått en feil, vennligst prøv igjen senere. [%d: \"%@\"]";
            strAlertTextLocating    = "Lokaliserer...";
            
            strTermYes              = "Ja";
            strTermNo               = "Nei";
            strTermMan              = "Mann";
            strTermBoy              = "Gutt";
            strTermWoman            = "Kvinne";
            strTermGirl             = "Jente";
            
            strFormatAge            = "%d år";
            
            strSeparatorAnd         = " og ";
        } else {
            strFooterTapToEdit      = "Tap text to edit.";
            strFooterOrigoSignature = "\n\nSent from Origo - http://origoapp.com";
            
            strButtonOK             = "OK";
            strButtonEdit           = "Edit";
            strButtonNext           = "Next";
            strButtonDone           = "Done";
            strButtonContinue       = "Continue";
            strButtonCancel         = "Cancel";
            strButtonSignOut        = "Log out";
            
            strAlertTextNoInternet  = "No internet connection.";
            strAlertTextServerError = "An error has occurred. Please try again later. [%d: \"%@\"]";
            strAlertTextLocating    = "Locating...";
            
            strTermYes              = "Yes";
            strTermNo               = "No";
            strTermMan              = "Man";
            strTermBoy              = "Boy";
            strTermWoman            = "Woman";
            strTermGirl             = "Girl";
            
            strFormatAge            = "%d years";
            
            strSeparatorAnd         = " and ";
        }
    }
    
    
    /* ==== Default strings ==== */
    
    public String strDefaultResidenceName;
    
    private void setDefaultStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strDefaultResidenceName           = "Min husstand";
        } else {
            strDefaultResidenceName           = "My place";
        }
    }
    
    /* ==== OAuthView strings ==== */
    
    public String strLabelSignIn;
    public String strLabelActivate;
    
    public String strFooterSignInOrRegister;
    public String strFooterActivateUser;
    public String strFooterActivateEmail;
    
    public String strPlaceholderAuthEmail;
    public String strPlaceholderPassword;
    public String strPlaceholderActivationCode;
    public String strPlaceholderRepeatPassword;
    
    public String strButtonHaveCode;
    public String strButtonStartOver;
    
    public String strAlertTitleActivationFailed;
    public String strAlertTextActivationFailed;
    public String strAlertTitleWelcomeBack;
    public String strAlertTextWelcomeBack;
    
    private void setAuthViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strLabelSignIn                = "Logg på eller registrer deg";
            strLabelActivate              = "Oppgi aktiveringskode";
            
            strFooterSignInOrRegister     = "Når du registrerer deg, vil du motta en epost med en aktiveringskode som du må oppgi i neste steg.";
            strFooterActivateUser         = "Aktiveringskoden er sendt til %@. Du kan komme tilbake senere om du ikke har tilgang til eposten din her og nå.";
            strFooterActivateEmail        = "Aktiveringskoden er sendt til %@.";
            
            strPlaceholderAuthEmail       = "Epostadressen din";
            strPlaceholderPassword        = "Passordet ditt";
            strPlaceholderActivationCode  = "Aktiveringskode fra epost";
            strPlaceholderRepeatPassword  = "Gjenta passordet ditt";
            
            strButtonHaveCode             = "Har kode";
            strButtonStartOver            = "Gå tilbake";
            
            strAlertTitleActivationFailed = "Aktivering mislyktes";
            strAlertTextActivationFailed  = "Det ser ut til at du enten har mistet aktiveringskoden som vi sendte deg på epost, eller at du har glemt passordet ditt. La oss starte på nytt.";
            strAlertTitleWelcomeBack      = "Velkommen tilbake!";
            strAlertTextWelcomeBack       = "Om du har aktiveringskoden som ble sendt til %@, så kan du aktivere medlemskapet ditt nå. Om ikke, kan du gå tilbake og starte på nytt.";
        } else {
            strLabelSignIn                = "Sign in or sign up";
            strLabelActivate              = "Enter activation code";
            
            strFooterSignInOrRegister     = "When you register, you will receive an email with an activation code that you must enter in the next step.";
            strFooterActivateUser         = "The activation code has been sent to %@. You can come back later if you don't have access to your email at this time.";
            strFooterActivateEmail        = "The activation code has been sent to %@.";
            
            strPlaceholderAuthEmail       = "Your email address";
            strPlaceholderPassword        = "Your password";
            strPlaceholderActivationCode  = "Activation code from email";
            strPlaceholderRepeatPassword  = "Repeat your password";
            
            strButtonHaveCode             = "Have code";
            strButtonStartOver            = "Start over";
            
            strAlertTitleActivationFailed = "Activation failed";
            strAlertTextActivationFailed  = "It looks like you may have lost the activation code that we emailed to you, or forgotten your password. Let's start over.";
            strAlertTitleWelcomeBack      = "Welcome back!";
            strAlertTextWelcomeBack       = "If you have handy the activation code sent to %@, you can now activate your membership. If not, you may go back and start over.";
        }
    }
    
    
    /* ==== OOrigoListView strings ==== */
    
    public String strViewTitleOrigo;
    
    public String strHeaderWardsOrigos;
    public String strHeaderMyOrigos;
    
    public String strFooterOrigoCreationFirst;
    public String strFooterOrigoCreation;
    public String strFooterOrigoCreationWards;
    
    public String strButtonCountryOther;
    
    public String strAlertTitleListedUserRegistration;
    public String strAlertTextListedUserRegistration;
    public String strAlertTitleIncompleteRegistration;
    public String strAlertTextIncompleteRegistration;
    public String strAlertTitleCountryOther;
    public String strAlertTextCountryOther;
    public String strAlertTextCountrySupported;
    public String strAlertTextCountryUnsupported;
    
    public String strSheetPromptCountry;
    public String strSheetPromptOrigoType;
    
    public String strTextNoOrigos;
    
    public String strTermYourChild;
    public String strTermHimOrHer;
    public String strTermForName;
    
    private void setOrigoListViewStrings(String language)
    {
        strViewTitleOrigo                       = "Origo";
        
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strHeaderWardsOrigos                = "Barnas origo";
            strHeaderMyOrigos                   = "Mine origo";
            
            strFooterOrigoCreationFirst         = "Trykk [+] for å opprette et origo";
            strFooterOrigoCreation              = "Trykk [+] for å opprette et nytt origo";
            strFooterOrigoCreationWards         = "for deg selv, eller velg %@ for å opprette et origo for %@";
            
            strButtonCountryOther               = "Et annet land";
            
            strAlertTitleListedUserRegistration = "Velkommen til Origo";
            strAlertTextListedUserRegistration  = "Kontroller at opplysningene dine er riktige, samt legg inn opplysninger som %@ ikke hadde adgang til å oppgi da %@ inviterte deg.";  
            strAlertTitleIncompleteRegistration = "Ufullstendig registrering";
            strAlertTextIncompleteRegistration  = "Du må fullføre registreringen før du kan begynne å bruke Origo.";
            strAlertTitleCountryOther           = "Andre land";
            strAlertTextCountryOther            = "For å kunne velge et annet land, må du først angi det som regionformat i systeminnstillingene.\n(Innstillinger > Generelt > Internasjonalt)";
            strAlertTextCountrySupported        = "Nye origo vil bli tilpasset %@.";
            strAlertTextCountryUnsupported      = "Lokale tilpasninger er foreløpig ikke tilgjengelige for %@. Nye origo vil bli opprettet uten lokale tilpasninger.";
            
            strSheetPromptCountry               = "Det nye origoet vil om mulig bli tilpasset landet du bor i. Hva er bostedslandet ditt?";
            strSheetPromptOrigoType             = "Hva slags origo ønsker du å opprette";
            
            strTextNoOrigos                     = "(Ingen origo)";
            
            strTermYourChild                    = "et av barna";
            strTermHimOrHer                     = "ham eller henne";
            strTermForName                      = "for %@";
        } else {
            strHeaderWardsOrigos                = "The kids' origos";
            strHeaderMyOrigos                   = "My origos";
            
            strFooterOrigoCreationFirst         = "Tap [+] to create an origo";
            strFooterOrigoCreation              = "Tap [+] to create a new origo";
            strFooterOrigoCreationWards         = "for yourself. Select %@ to create an origo for %@";
            
            strButtonCountryOther               = "Another country";
            
            strAlertTitleListedUserRegistration = "Welcome to Origo";
            strAlertTextListedUserRegistration  = "Please verify your details and provide the information that %@ was not authorised to enter when %@ invited you.";  
            strAlertTitleIncompleteRegistration = "Incomplete registration";
            strAlertTextIncompleteRegistration  = "You must complete your registration before you can start using Origo.";
            strAlertTitleCountryOther           = "Other countries";
            strAlertTextCountryOther            = "To choose another country, you must first specify it as region format in you system settings.\n(Settings > General > International)";
            strAlertTextCountrySupported        = "New origos will be adapted for %@.";
            strAlertTextCountryUnsupported      = "Local adaptations are not yet available for %@. New origos will be created without local adaptations.";
            
            strSheetPromptCountry               = "The new origo will if possible be adapted for the country where you live. What is your country of residence?";
            strSheetPromptOrigoType             = "What sort of origo du you want to create";
            
            strTextNoOrigos                     = "(No origos)";
            
            strTermYourChild                    = "your child";
            strTermHimOrHer                     = "him or her";
            strTermForName                      = "for %@";
        }
    }
    
    
    /* ==== OOrigoView strings ==== */
    
    public String strLabelAddress;
    public String strLabelPurpose;
    public String strLabelDescriptionText;
    public String strLabelTelephone;
    
    public String strPlaceholderResidenceName;
    public String strPlaceholderAddress;
    public String strPlaceholderPurpose;
    public String strPlaceholderDescriptionText;
    public String strPlaceholderTelephone;
    
    public String strFooterResidence;
    public String strFooterFriends;
    public String strFooterTeam;
    public String strFooterOrganisation;
    public String strFooterPreschoolClass;
    public String strFooterSchoolClass;
    public String strFooterPlaymates;
    public String strFooterTeamMinor;
    public String strFooterOther;
    
    public String strButtonAddMemberResidence;
    public String strButtonAddMemberFriends;
    public String strButtonAddMemberTeam;
    public String strButtonAddMemberOrganisation;
    public String strButtonAddMemberPreschoolClass;
    public String strButtonAddMemberSchoolClass;
    public String strButtonAddMemberPlaymates;
    public String strButtonAddMemberTeamMinor;
    public String strButtonAddMemberOther;
    public String strButtonAddContactTeam;
    public String strButtonAddContactOrganisation;
    public String strButtonAddContactOther;
    public String strButtonAddContactPreschoolClass;
    public String strButtonAddContactSchoolClass;
    public String strButtonAddContactTeamMinor;
    public String strButtonAddParentContact;
    public String strButtonAbout;
    public String strButtonShowInMap;
    public String strButtonNewHousemate;
    public String strButtonOtherGuardian;
    public String strButtonDeleteMember;
    
    private void setOrigoViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strLabelAddress                   = "Adresse";
            strLabelPurpose                   = "Formål";
            strLabelDescriptionText           = "Beskrivelse";
            strLabelTelephone                 = "Telefon";
            
            strPlaceholderResidenceName       = "Kallenavn for denne adressen";
            strPlaceholderAddress             = "Gateadresse\nPostnummer og -sted";
            strPlaceholderPurpose             = "Formålet med dette origoet";
            strPlaceholderDescriptionText     = "En valgfri beskrivelse";
            strPlaceholderTelephone           = "Telefonnummer";
            
            strFooterResidence                = "Trykk [+] for å legge til medlemmer i husstanden.";
            strFooterFriends                  = "Trykk [+] for å legge til venner.";
            strFooterTeam                     = "Trykk [+] for å legge til spillere på laget.";
            strFooterOrganisation             = "Trykk [+] for å legge til medlemmer.";
            strFooterPreschoolClass           = "Trykk [+] for å legge til barn i avdelingen.";
            strFooterSchoolClass              = "Trykk [+] for å legge til elever i klassen.";
            strFooterPlaymates                = "Trykk [+] for å legge til venner i gjengen.";
            strFooterTeamMinor                = "Trykk [+] for å legge til deltakere på laget.";
            strFooterOther                    = "Trykk [+] for å legge til medlemmer.";
            
            strButtonAddMemberResidence       = "Nytt medlem i husstanden";
            strButtonAddMemberFriends         = "Legg til venn";
            strButtonAddMemberTeam            = "Legg til spiller på laget";
            strButtonAddMemberOrganisation    = "Legg til medlem";
            strButtonAddMemberPreschoolClass  = "Legg til barn";
            strButtonAddMemberSchoolClass     = "Legg til elev";
            strButtonAddMemberPlaymates       = "Legg til venn i gjengen";
            strButtonAddMemberTeamMinor       = "Legg til deltaker på laget";
            strButtonAddMemberOther           = "Legg til medlem";
            strButtonAddContactTeam           = "Legg til trener";
            strButtonAddContactOrganisation   = "Legg til kontaktperson";
            strButtonAddContactOther          = "Lekk til kontaktperson";
            strButtonAddContactPreschoolClass = "Legg til lærer/assistent";
            strButtonAddContactSchoolClass    = "Legg til lærer";
            strButtonAddContactTeamMinor      = "Legg til trener";
            strButtonAddParentContact         = "Legg til foreldrekontakt";
            strButtonAbout                    = "Om %@";
            strButtonShowInMap                = "Vis på kart";
            strButtonNewHousemate             = "Ny bofelle";   
            strButtonOtherGuardian            = "Annen foresatt";   
            strButtonDeleteMember             = "Meld ut";
        } else {
            strLabelAddress                   = "Address";
            strLabelPurpose                   = "Purpose";
            strLabelDescriptionText           = "Description";
            strLabelTelephone                 = "Telephone";
            
            strPlaceholderResidenceName       = "Nickname for this address";
            strPlaceholderAddress             = "Street address\nPostal code and city/town";
            strPlaceholderPurpose             = "The purpose of this origo";
            strPlaceholderDescriptionText     = "An optional description";
            strPlaceholderTelephone           = "Telephone number";
            
            strFooterResidence                = "Tap [+] to add members to the household.";
            strFooterFriends                  = "Tap [+] to add friends.";
            strFooterTeam                     = "Tap [+] to add players.";
            strFooterOrganisation             = "Tap [+] to add members.";
            strFooterPreschoolClass           = "Tap [+] to add pupils to the class.";
            strFooterSchoolClass              = "Tap [+] to add pupils to the class.";
            strFooterPlaymates                = "Tap [+] to add friends to the flock.";
            strFooterTeamMinor                = "Tap [+] to add players.";
            strFooterOther                    = "Tap [+] to add members.";
            
            strButtonAddMemberResidence       = "New household member";
            strButtonAddMemberFriends         = "Add friend";
            strButtonAddMemberTeam            = "Add player";
            strButtonAddMemberOrganisation    = "Add member";
            strButtonAddMemberPreschoolClass  = "Add child";
            strButtonAddMemberSchoolClass     = "Add pupil";
            strButtonAddMemberPlaymates       = "Add friend";
            strButtonAddMemberTeamMinor       = "Add player";
            strButtonAddMemberOther           = "Add member";
            strButtonAddContactTeam           = "Add coach";
            strButtonAddContactOrganisation   = "Add contact";
            strButtonAddContactOther          = "Add contact";
            strButtonAddContactPreschoolClass = "Add teacher";
            strButtonAddContactSchoolClass    = "Add teacher";
            strButtonAddContactTeamMinor      = "Add coach";
            strButtonAddParentContact         = "Add parent contact";
            strButtonAbout                    = "About %@";
            strButtonShowInMap                = "Show in map";
            strButtonNewHousemate             = "New housemate";
            strButtonOtherGuardian            = "Other guardian";   
            strButtonDeleteMember             = "Remove";
        }
    }
    
    
    /* ==== OMemberView strings ==== */
    
    public String strViewTitleAboutMe;
    
    public String strLabelAge;
    public String strLabelDateOfBirth;
    public String strLabelMobilePhone;
    public String strLabelEmail;
    
    public String strPlaceholderName;
    public String strPlaceholderPhoto;
    public String strPlaceholderDateOfBirth;
    public String strPlaceholderMobilePhone;
    public String strPlaceholderEmail;
    
    public String strFooterOrigoInviteAlert;
    public String strFooterJuvenileOrigoGuardian;
    
    public String strButtonParentToSome;
    public String strButtonAddAddress;
    public String strButtonChangePassword;
    public String strButtonEditRelations;
    public String strButtonCorrectGender;
    public String strButtonNewAddress;
    public String strButtonAllMembers;
    public String strButtonAllContacts;
    public String strButtonAllGuardians;
    public String strButtonInviteToHousehold;
    public String strButtonMergeHouseholds;
    
    public String strAlertTitleMemberExists;
    public String strAlertTextMemberExists;
    public String strAlertTitleUserEmailChange;
    public String strAlertTextUserEmailChange;
    public String strAlertTitleFailedEmailChange;
    public String strAlertTextFailedEmailChange;

    public String strSheetPromptEmailRecipient;
    public String strSheetPromptTextRecipient;
    public String strSheetPromptCallRecipient;
    public String strSheetPromptExistingResidence;
    
    public String strQuestionArgumentGender;
    public String strQuestionArgumentGenderMinor;
    
    public String strTermHisFather;
    public String strTermHerFather;
    public String strTermHisMother;
    public String strTermHerMother;
    
    private void setMemberViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strViewTitleAboutMe             = "Om meg";
            
            strLabelAge                     = "Alder";
            strLabelDateOfBirth             = "Født";
            strLabelMobilePhone             = "Mobil";
            strLabelEmail                   = "Epost";
            
            strPlaceholderName              = "Navn";
            strPlaceholderPhoto             = "Bilde";
            strPlaceholderDateOfBirth       = "Fødselsdato";
            strPlaceholderMobilePhone       = "Mobilnummer";
            strPlaceholderEmail             = "En gyldig epostadresse";
            
            strFooterOrigoInviteAlert       = "En invitasjon vil bli sendt om du oppgir en epost-adresse.";
            strFooterJuvenileOrigoGuardian  = "Før du kan registrere en mindreårig, må du registrere hans eller hennes foresatte.";
            
            strButtonParentToSome           = "Til noen av dem";
            strButtonAddAddress             = "Legg til en adresse";
            strButtonChangePassword         = "Endre passord";
            strButtonEditRelations          = "Rediger relasjoner";
            strButtonCorrectGender          = "Korriger kjønn";
            strButtonNewAddress             = "Ny adresse";
            strButtonAllMembers             = "Alle medlemmer";
            strButtonAllGuardians           = "Alle foresatte";
            strButtonAllContacts            = "Alle kontaktpersoner";
            strButtonInviteToHousehold      = "Inviter til husstanden";
            strButtonMergeHouseholds        = "Slå sammen husstandene";
            
            strAlertTitleMemberExists       = "Allerede registrert";
            strAlertTextMemberExists        = "%@ (%@) er allerede registrert i \"%@\". Vennligst oppgi en annen epost-adresse, eller avbryt registreringen.";
            strAlertTitleUserEmailChange    = "Ny epost-adresse";
            strAlertTextUserEmailChange     = "Du er i ferd med å endre epost-adressen din fra %@ til %@. Du må ha tilgang til den nye adressen for å aktivere endringen. Ønsker du å fortsette?";
            strAlertTitleFailedEmailChange  = "Aktivering mislyktes";
            strAlertTextFailedEmailChange   = "Aktivering av epost-adressen %@ mislyktes. Prøv igjen, eller trykk Avbryt for avbryte endringen.";
            
            strSheetPromptEmailRecipient    = "Hvem vil du sende epost til?";
            strSheetPromptTextRecipient     = "Hvem vil du sende tekstmelding til?";
            strSheetPromptCallRecipient     = "Hvem vil du ringe?";
            strSheetPromptExistingResidence = "%@ er allerede medlem av en husstand. Vil du invitere %@ til også å bli med i din husstand, eller ønsker du å slå husstandene deres sammen til én?";
            
            strQuestionArgumentGender       = "kvinne eller mann";
            strQuestionArgumentGenderMinor  = "jente eller gutt";
            
            strTermHisFather                = "faren hans";
            strTermHerFather                = "faren hennes";
            strTermHisMother                = "moren hans";
            strTermHerMother                = "moren hennes";
        } else {
            strViewTitleAboutMe             = "About me";
            
            strLabelAge                     = "Age";
            strLabelDateOfBirth             = "Born";
            strLabelMobilePhone             = "Mobile";
            strLabelEmail                   = "Email";
            
            strPlaceholderName              = "Name";
            strPlaceholderPhoto             = "Photo";
            strPlaceholderDateOfBirth       = "Date of birth";
            strPlaceholderMobilePhone       = "Mobile phone number";
            strPlaceholderEmail             = "A valid email address";
            
            strFooterOrigoInviteAlert       = "An invitation will be sent if you provide an email address.";
            strFooterJuvenileOrigoGuardian  = "Before you can register a minor, you must register his or her parents/guardians.";
            
            strButtonParentToSome           = "To some of them";
            strButtonAddAddress             = "Add an address";
            strButtonChangePassword         = "Change password";
            strButtonEditRelations          = "Edit relations";
            strButtonCorrectGender          = "Correct gender";
            strButtonNewAddress             = "New address";
            strButtonAllMembers             = "All members";
            strButtonAllContacts            = "All contacts";
            strButtonAllGuardians           = "All guardians";
            strButtonInviteToHousehold      = "Invite to household";
            strButtonMergeHouseholds        = "Merge households";
            
            strAlertTitleMemberExists       = "Already registered";
            strAlertTextMemberExists        = "%@ (%@) is already registered in '%@'. Please enter a different email address, or cancel the registration.";
            strAlertTitleUserEmailChange    = "New email address";
            strAlertTextUserEmailChange     = "You are about to change your email address from %@ to %@. You need access to the new address to activate this change. Do you want to continue?";
            strAlertTitleFailedEmailChange  = "Activation failed";
            strAlertTextFailedEmailChange   = "The email address %@ could not be activated. Please try again, or tap Cancel to cancel the change.";
            
            strSheetPromptEmailRecipient    = "Who do you want to email?";
            strSheetPromptTextRecipient     = "Who do you want to text?";
            strSheetPromptCallRecipient     = "Who do you want to call?";
            strSheetPromptExistingResidence = "%@ is already member of a household. Would you like to invite %@ to join your household as well, or do you want to merge your households into one?";
            
            strQuestionArgumentGender       = "a woman or a man";
            strQuestionArgumentGenderMinor  = "a girl or a boy";
            
            strTermHisFather                = "his father";
            strTermHerFather                = "her father";
            strTermHisMother                = "his mother";
            strTermHerMother                = "her mother";
        }
    }
    
    
    /* ==== OCalendarView strings ==== */
    
    public String strViewTitleCalendar;
    
    private void setCalendarViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strViewTitleCalendar = "Kalender";
        } else {
            strViewTitleCalendar = "Calendar";
        }
    }
    
    
    /* ==== OTaskView strings ==== */
    
    public String strViewTitleTasks;
    
    private void setTaskViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strViewTitleTasks = "Oppgaver";
        } else {
            strViewTitleTasks = "Tasks";
        }
    }
    
    
    /* ==== OMessageBoardView strings ==== */
    
    public String strViewTitleMessages;
    
    public String strDefaultMessageBoardName;
    
    private void setMessageBoardViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strViewTitleMessages       = "Meldinger";
            
            strDefaultMessageBoardName = "Oppslagstavle";
        } else {
            strViewTitleMessages       = "Messages";
            
            strDefaultMessageBoardName = "Message board";
        }
    }
    
    
    /* ==== OSettingListView strings ==== */
    
    public String strViewTitleSettings;
    
    public String strSettingLabelCountry;
    
    private void setSettingListViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strViewTitleSettings   = "Innstillinger";
            
            strSettingLabelCountry = "Nye origo tilpasses";
        } else {
            strViewTitleSettings   = "Settings";
            
            strSettingLabelCountry = "Adapt origos for";
        }
    }
    
    
    /* ==== OSettingView strings ==== */
    
    public String strSettingTitleCountry;
    
    public String strLabelCountrySettings;
    public String strLabelCountryLocate;
    
    public String strFooterCountryInfo;
    public String strFooterCountryInfoNote;
    public String strFooterCountryInfoLocate;
    
    private void setSettingViewStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strSettingTitleCountry     = "Land";
            
            strLabelCountrySettings    = "Lokal innstilling";
            strLabelCountryLocate      = "Dette landet";
            
            strFooterCountryInfo       = "Lokale tilpasninger er foreløpig kun tilgjengelig for %@";
            strFooterCountryInfoNote   = ", og vil ikke bli benyttet om du velger et annet land.";
            strFooterCountryInfoLocate = "Om du ønsker å angi landet du befinner deg i, må du tillate Origo å bruke stedstjenestene.\n(Innstillinger > Personvern > Sted)";
        } else {
            strSettingTitleCountry     = "Country";
            
            strLabelCountrySettings    = "Local setting";
            strLabelCountryLocate      = "This country";
            
            strFooterCountryInfo       = "Local adaptations are currently only available for %@";
            strFooterCountryInfoNote   = ", and will not be applied if you select a different country.";
            strFooterCountryInfoLocate = "If you wish to specify the country you're in, you must permit Origo to use location services.\n(Settings > Privacy > Location)";
        }
    }
    
    
    /* ==== Origo type strings ==== */
    
    public String strOrigoTitleResidence;
    public String strOrigoTitleFriends;
    public String strOrigoTitleTeam;
    public String strOrigoTitleOrganisation;
    public String strOrigoTitlePreschoolClass;
    public String strOrigoTitleSchoolClass;
    public String strOrigoTitlePlaymates;
    public String strOrigoTitleMinorTeam;
    public String strOrigoTitleOther;
    
    public String strNewOrigoTitleContactList;
    public String strNewOrigoTitleResidence;
    public String strNewOrigoTitleFriends;
    public String strNewOrigoTitleTeam;
    public String strNewOrigoTitleOrganisation;
    public String strNewOrigoTitlePreschoolClass;
    public String strNewOrigoTitleSchoolClass;
    public String strNewOrigoTitlePlaymates;
    public String strNewOrigoTitleMinorTeam;
    public String strNewOrigoTitleOther;
    
    public String strMemberListTitleContactList;
    public String strMemberListTitleResidence;
    public String strMemberListTitleFriends;
    public String strMemberListTitleTeam;
    public String strMemberListTitleOrganisation;
    public String strMemberListTitlePreschoolClass;
    public String strMemberListTitleSchoolClass;
    public String strMemberListTitlePlaymates;
    public String strMemberListTitleMinorTeam;
    public String strMemberListTitleOther;
    
    public String strNewMemberTitleContactList;
    public String strNewMemberTitleResidence;
    public String strNewMemberTitleFriends;
    public String strNewMemberTitleTeam;
    public String strNewMemberTitleOrganisation;
    public String strNewMemberTitlePreschoolClass;
    public String strNewMemberTitleSchoolClass;
    public String strNewMemberTitlePlaymates;
    public String strNewMemberTitleMinorTeam;
    public String strNewMemberTitleOther;
    
    private void setOrigoTypeStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strOrigoTitleResidence           = "Husstand";
            strOrigoTitleFriends             = "Vennegruppe";
            strOrigoTitleTeam                = "Lag, idrettsgruppe";
            strOrigoTitleOrganisation        = "Organisasjon, forening";
            strOrigoTitlePreschoolClass      = "Barnehageavdeling";
            strOrigoTitleSchoolClass         = "Skoleklasse";
            strOrigoTitlePlaymates           = "Vennegjeng";
            strOrigoTitleMinorTeam           = "Lag/idrettsgruppe";
            strOrigoTitleOther               = "Annet formål";
            
            strNewOrigoTitleResidence        = "Ny adresse";
            strNewOrigoTitleContactList      = "Ny kontaktliste";
            strNewOrigoTitleFriends          = "Ny vennegruppe";
            strNewOrigoTitleTeam             = "Ny idrettsgruppe";
            strNewOrigoTitleOrganisation     = "Ny organisasjon";
            strNewOrigoTitlePreschoolClass   = "Ny barnehageavdeling";
            strNewOrigoTitleSchoolClass      = "Ny skoleklasse";
            strNewOrigoTitlePlaymates        = "Ny vennegjeng";
            strNewOrigoTitleMinorTeam        = "Ny idrettsgruppe";
            strNewOrigoTitleOther            = "Nytt origo";
            
            strMemberListTitleResidence      = "I husstanden";
            strMemberListTitleContactList    = "Kontakter";
            strMemberListTitleFriends        = "I gruppa";
            strMemberListTitleTeam           = "På laget";
            strMemberListTitleOrganisation   = "Medlemmer";
            strMemberListTitlePreschoolClass = "I avdelingen";
            strMemberListTitleSchoolClass    = "I klassen";
            strMemberListTitlePlaymates      = "I gjengen";
            strMemberListTitleMinorTeam      = "På laget";
            strMemberListTitleOther          = "Medlemmer";
            
            strNewMemberTitleResidence       = "I husstanden";
            strNewMemberTitleContactList     = "Ny kontakt";
            strNewMemberTitleFriends         = "I gruppa";
            strNewMemberTitleTeam            = "Ny deltaker";
            strNewMemberTitleOrganisation    = "Nytt medlem";
            strNewMemberTitlePreschoolClass  = "Ny i avdelingen";
            strNewMemberTitleSchoolClass     = "Ny klassekompis";
            strNewMemberTitlePlaymates       = "I gjengen";
            strNewMemberTitleMinorTeam       = "Ny deltaker";
            strNewMemberTitleOther           = "Nytt medlem";
        } else {
            strOrigoTitleResidence           = "Household";
            strOrigoTitleFriends             = "Party of friends";
            strOrigoTitleTeam                = "Team/sports group";
            strOrigoTitleOrganisation        = "Organisation";
            strOrigoTitlePreschoolClass      = "Preschool class";
            strOrigoTitleSchoolClass         = "School class";
            strOrigoTitlePlaymates           = "Flock of friends";
            strOrigoTitleMinorTeam           = "Team/sports group";
            strOrigoTitleOther               = "General purpose";
            
            strNewOrigoTitleResidence        = "New address";
            strNewOrigoTitleContactList      = "New contact list";
            strNewOrigoTitleFriends          = "New party of friends";
            strNewOrigoTitleTeam             = "New sports group";
            strNewOrigoTitleOrganisation     = "New organisation";
            strNewOrigoTitlePreschoolClass   = "New preschool class";
            strNewOrigoTitleSchoolClass      = "New school class";
            strNewOrigoTitlePlaymates        = "New flock";
            strNewOrigoTitleMinorTeam        = "New sports group";
            strNewOrigoTitleOther            = "New origo";
            
            strMemberListTitleResidence      = "In the household";
            strMemberListTitleContactList    = "Contacts";
            strMemberListTitleFriends        = "In the party";
            strMemberListTitleTeam           = "On the team";
            strMemberListTitleOrganisation   = "Members";
            strMemberListTitlePreschoolClass = "In the class";
            strMemberListTitleSchoolClass    = "In the class";
            strMemberListTitlePlaymates      = "In the flock";
            strMemberListTitleMinorTeam      = "On the team";
            strMemberListTitleOther          = "Members";
            
            strNewMemberTitleResidence       = "In the household";
            strNewMemberTitleContactList     = "New contact";
            strNewMemberTitleFriends         = "In the party";
            strNewMemberTitleTeam            = "New participant";
            strNewMemberTitleOrganisation    = "New member";
            strNewMemberTitlePreschoolClass  = "New classmate";
            strNewMemberTitleSchoolClass     = "New classmate";
            strNewMemberTitlePlaymates       = "In the flock";
            strNewMemberTitleMinorTeam       = "New participant";
            strNewMemberTitleOther           = "New member";
        }
    }
    
    
    /* ==== Meta strings ==== */
    
    public String metaSupportedLanguages = "nb";
    
    public String metaContactRolesSchoolClass = "classTeacher|topicTeacher|specialEducationTeacher|assistantTeacher|headTeacher|parentRepresentative";
    public String strContactRoleClassTeacher;
    public String strContactRoleTopicTeacher;
    public String strContactRoleSpecialEducationTeacher;
    public String strContactRoleAssistantTeacher;
    public String strContactRoleHeadTeacher;
    public String strContactRoleParentRepresentative;
    
    public String metaContactRolesPreschoolClass = "preschoolClassTeacher|preschoolTeacher|preschoolAssistantTeacher";
    public String strContactRolePreschoolClassTeacher;
    public String strContactRolePreschoolTeacher;
    public String strContactRolePreschoolAssistantTeacher;
    
    public String metaContactRolesOrganisation = "chair|deputyChair|treasurer";
    public String strContactRoleChair;
    public String strContactRoleDeputyChair;
    public String strContactRoleTreasurer;
    
    public String metaContactRolesSportsTeam = "coach|assistantCoach";
    public String strContactRoleCoach;
    public String strContactRolessistantCoach;
    
    private void setMeta(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strContactRoleClassTeacher              = "Kontaktlærer";
            strContactRoleTopicTeacher              = "Faglærer";
            strContactRoleSpecialEducationTeacher   = "Spesiallærer";
            strContactRoleAssistantTeacher          = "Assistentlærer";
            strContactRoleHeadTeacher               = "Rektor";
            strContactRoleParentRepresentative      = "Klassekontakt";
            
            strContactRolePreschoolClassTeacher     = "Avdelingsleder";
            strContactRolePreschoolTeacher          = "Førskolelærer";
            strContactRolePreschoolAssistantTeacher = "Assistent";
            
            strContactRoleChair                     = "Formann";
            strContactRoleDeputyChair               = "Varamann";
            strContactRoleTreasurer                 = "Kasserer";
            
            strContactRoleCoach                     = "Trener";
            strContactRolessistantCoach             = "Assistenttrener";
        } else {
            strContactRoleClassTeacher              = "Teacher";
            strContactRoleTopicTeacher              = "Topic teacher";
            strContactRoleSpecialEducationTeacher   = "Special education teacher";
            strContactRoleAssistantTeacher          = "Assistant teacher";
            strContactRoleHeadTeacher               = "Head teacher";
            strContactRoleParentRepresentative      = "Parent representative";
            
            strContactRolePreschoolClassTeacher     = "Department head";
            strContactRolePreschoolTeacher          = "Teacher";
            strContactRolePreschoolAssistantTeacher = "Assistant";
            
            strContactRoleChair                     = "Chair";
            strContactRoleDeputyChair               = "Deputy chair";
            strContactRoleTreasurer                 = "Treasurer";
            
            strContactRoleCoach                     = "Coach";
            strContactRolessistantCoach             = "Assistant coach";
        }
    }
    
    
    /* ==== Language strings ==== */
    
    public String strQuestionTemplate;
    
    public String verbs    = "be";
    public String nouns    = "origo|father|mother|parent|guardian|contact|address";
    public String pronouns = "I|you|he|she";
    
    public String verbBe;
    
    public String nounOrigo;
    public String nounFather;
    public String nounMother;
    public String nounParent;
    public String nounGuardian;
    public String nounContact;
    public String nounAddress;
    
    public String pronounI;
    public String pronounYou;
    public String pronounHe;
    public String pronounShe;
    
    private void setLanguageStrings(String language)
    {
        if (language.equals(LANG_NORWEGIAN_BOKMAL)) {
            strQuestionTemplate = "{verb} {subject} {argument}?";
            
            verbBe              = "er|er|er|er|er|er";
            
            nounOrigo           = "-|-|-|-|mine origo|%@ sine origo";
            nounFather          = "far|faren|-|-|faren din|faren til %@";
            nounMother          = "mor|moren|-|-|moren din|moren til %@";
            nounParent          = "-|-|foreldre|foreldrene|foreldrene dine|foreldrene til %@";
            nounGuardian        = "foresatt|-|foresatte|-|-|-";
            nounContact         = "kontaktperson|-|kontaktpersoner|-|-|-";
            nounAddress         = "adresse|-|adresser|-|-|-";
            
            pronounI            = "jeg|meg|meg";
            pronounYou          = "du|deg|deg";
            pronounHe           = "han|ham|ham";
            pronounShe          = "hun|henne|henne";
        } else {
            strQuestionTemplate = "{verb} {subject} {argument}?";
            
            verbBe              = "am|are|is|are|are|are";
            
            nounOrigo           = "-|-|-|-|my origos|%@'s origos";
            nounFather          = "father|the father|-|-|your father|%@'s father";
            nounMother          = "mother|the mother|-|-|your mother|%@'s mother";
            nounParent          = "-|-|parents|the parents|your parents|%@'s parents";
            nounGuardian        = "guardian|-|guardians|-|-|-";
            nounContact         = "contact|-|contacts|-|-|-";
            nounAddress         = "address|-|addresses|-|-|-";
            
            pronounI            = "I|me|me";
            pronounYou          = "you|you|you";
            pronounHe           = "he|him|him";
            pronounShe          = "she|her|her";
        }
    }
}
