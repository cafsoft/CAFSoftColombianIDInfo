package cafsoft.colombianidinfo;

public final class ColombianIDInfo {

    public enum DocumentType {
        IDENTITY_CARD,
        CITIZENSHIP_CARD,
        OTHER
    }

    private DocumentType documentType = DocumentType.OTHER;
    private int documentNumber = 0;
    private String bloodType = "";
    private String dateOfBirth = "";
    private String gender = "";
    private String firstFamilyName = "";
    private String secondFamilyName = "";
    private String firstName = "";
    private String otherNames = "";

    private static String clean(String aPDF417Data) {

        return aPDF417Data.replaceAll("\0"," ");
    }

    private static String extract(String text, int iniPos, int size){

        return text.substring(iniPos, iniPos + size);
    }

    public static ColombianIDInfo builder(String aPDF417Data)
            throws Exception {
        ColombianIDInfo card = new ColombianIDInfo();
        int disp = 0;

        if (aPDF417Data.charAt(0) == '0'){
            card.setDocumentType(DocumentType.CITIZENSHIP_CARD);
        }
        else if (aPDF417Data.charAt(0) == 'I') {
            card.setDocumentType(DocumentType.IDENTITY_CARD);
            disp = 1;
        }

        if (aPDF417Data.length() >= (169 + disp)) {
            String data = extract(aPDF417Data, 0, 169 + disp);
            data = clean(data);

            // Extract document number
            String documentNumber = extract(data,48, 10).trim();
            card.setDocumentNumber(Integer.parseInt(documentNumber));

            // Extract first family name
            String firstFamilyName = extract(data, 58 + disp, 23).trim();
            card.setFirstFamilyName(firstFamilyName);

            // Extract second family name
            String secondFamilyName = extract(data, 81 + disp, 23).trim();
            card.setSecondFamilyName(secondFamilyName);

            // Extract firstname
            String firstName = extract(data, 104 + disp, 23).trim();
            card.setFirstName(firstName);

            // Extract other names
            String otherNames = extract(data,127 + disp, 23).trim();
            card.setOtherNames(otherNames);

            // Extract gender
            String gender = extract(data, 151 + disp, 1).trim();
            card.setGender(gender);

            // Extract date of birth
            String dateOfBirth = extract(data,152 + disp, 8).trim();
            card.setDateOfBirth(dateOfBirth);

            // Extract blood type
            String bloodType = extract(data,166 + disp, 3).trim();
            card.setBloodType(bloodType);
        }

        return card;
    }

    public String getFamilyNames(){

        return (getFirstFamilyName() + " " + getSecondFamilyName()).trim();
    }

    public String getNames(){

        return (getFirstName() + " " + getOtherNames()).trim();
    }


    public String getFullname() {

        return getFamilyNames() + " " + getNames();
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getFirstFamilyName() {
        return firstFamilyName;
    }

    public void setFirstFamilyName(String firstFamilyName) {
        this.firstFamilyName = firstFamilyName;
    }

    public String getSecondFamilyName() {
        return secondFamilyName;
    }

    public void setSecondFamilyName(String secondFamilyName) {
        this.secondFamilyName = secondFamilyName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}