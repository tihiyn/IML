import java.util.List;
import java.util.UUID;

interface DocumentGenerator {
    boolean supports(String docType);
    UUID generate(UUID requestId);
}

// @Service
class CsNoticeDocumentGenerator implements DocumentGenerator {
    private static final String CS_NOTICE_DOC_TYPE = "cs_notice";

    @Override
    public boolean supports(String docType) {
        return CS_NOTICE_DOC_TYPE.equalsIgnoreCase(docType);
    }

    @Override
    public UUID generate(UUID requestId) {
        // генерация документа
        return UUID.randomUUID();
    }
}

// @Service
class DocumentService {
    private final List<DocumentGenerator> docGens;

    DocumentService(List<DocumentGenerator> docGens) {
        this.docGens = docGens;
    }

    public UUID generateDocument(UUID requestId, String docType) {
        return docGens.stream()
            .filter(dg -> dg.supports(docType))
            .findFirst()
            .orElseThrow(UnsupportedOperationException::new)
            .generate(requestId);
    }
}


public class Task12 {
}
