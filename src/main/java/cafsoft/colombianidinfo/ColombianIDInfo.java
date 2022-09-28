package cafsoft.colombianidinfo;

public final class ColombianIDInfo {

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

        return aPDF417Data.replaceAll("\0", " ");
    }

    private static String extract(String text, int iniPos, int size) {

        return text.substring(iniPos, iniPos + size);
    }

    public static ColombianIDInfo builder(String aPDF417Data)
            throws Exception {

        ColombianIDInfo info = null;
        int disp = 0;

        if (aPDF417Data.length() == 531) {
            disp = 0;
        } else if (aPDF417Data.length() == 532) {
            disp = 1;
        } else {
            throw new Exception();
        }

        info = new ColombianIDInfo();
        if (aPDF417Data.charAt(0) == '0') {
            info.setDocumentType(DocumentType.CITIZENSHIP_CARD);
        } else if (aPDF417Data.charAt(0) == 'I') {
            info.setDocumentType(DocumentType.IDENTITY_CARD);
            disp = 1;
        } else
            throw new Exception();

        String data = extract(aPDF417Data, 0, 169 + disp);
        data = clean(data);

        // Extract document number
        String documentNumber = extract(data, 48, 10).trim();
        info.setDocumentNumber(Integer.parseInt(documentNumber));

        // Extract first family name
        String firstFamilyName = extract(data, 58 + disp, 23).trim();
        info.setFirstFamilyName(firstFamilyName);

        // Extract second family name
        String secondFamilyName = extract(data, 81 + disp, 23).trim();
        info.setSecondFamilyName(secondFamilyName);

        // Extract firstname
        String firstName = extract(data, 104 + disp, 23).trim();
        info.setFirstName(firstName);

        // Extract other names
        String otherNames = extract(data, 127 + disp, 23).trim();
        info.setOtherNames(otherNames);

        // Extract gender
        String gender = extract(data, 151 + disp, 1).trim();
        info.setGender(gender);

        // Extract date of birth
        final String dateOfBirth = extract(data, 152 + disp, 8).trim();
        info.setDateOfBirth(dateOfBirth);

        // Extract blood type
        final String bloodType = extract(data, 166 + disp, 3).trim();
        String abo = "";
        char rh = '?';
        if (bloodType.length() == 3) {
            abo = extract(bloodType, 0, 2).trim();
            rh = bloodType.charAt(2);
        }else if (bloodType.length() == 2) {
            abo = extract(bloodType, 0, 1).trim();
            rh = bloodType.charAt(1);
        } else
            throw new Exception();

        if ((abo.equals("A") || abo.equals("B") || abo.equals("AB")|| abo.equals("O")) && (rh == '+' || rh == '-'))
            info.setBloodType(abo + rh);
        else
            throw new Exception();

        return info;
    }

    public String getFamilyName() {

        return (getFirstFamilyName() + " " + getSecondFamilyName()).trim();
    }

    public String getName() {

        return (getFirstName() + " " + getOtherNames()).trim();
    }

    public String getFullname() {

        return getFamilyName() + " " + getName();
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

    public enum DocumentType {
        IDENTITY_CARD,
        CITIZENSHIP_CARD,
        OTHER
    }
}