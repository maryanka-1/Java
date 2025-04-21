package preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {

    @Override
    public String change(String text) {
        return text.toLowerCase();
    }
}
