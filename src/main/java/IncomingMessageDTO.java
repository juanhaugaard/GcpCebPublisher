
import java.util.Collection;

public class IncomingMessageDTO {
    private String application;
    private String correlationID;
    private String eventName;
    private String locnNbr;
    private String lgclLocnNbr;
    private String containerBarcode;
    private String asn;
    private String po;
    private String receipt;
    private String keyrec;
    private String suffix;
    private String reason;
    private String receiptStatus;
    private String replayFlag;
    private String orderSource;
    private String orderNumber;
    private String timestamp;
    private Collection<String> lockCodes;
    private Collection<IncomingItemDTO> items;

    public IncomingMessageDTO() {
    }

    public String getApplication() {
        return this.application;
    }

    public String getCorrelationID() {
        return this.correlationID;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getLocnNbr() {
        return this.locnNbr;
    }

    public String getLgclLocnNbr() {
        return this.lgclLocnNbr;
    }

    public String getContainerBarcode() {
        return this.containerBarcode;
    }

    public String getAsn() {
        return this.asn;
    }

    public String getPo() {
        return this.po;
    }

    public String getReceipt() {
        return this.receipt;
    }

    public String getKeyrec() {
        return this.keyrec;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getReason() {
        return this.reason;
    }

    public String getReceiptStatus() {
        return this.receiptStatus;
    }

    public String getReplayFlag() {
        return this.replayFlag;
    }

    public String getOrderSource() {
        return this.orderSource;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public Collection<String> getLockCodes() {
        return this.lockCodes;
    }

    public Collection<IncomingItemDTO> getItems() {
        return this.items;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setLocnNbr(String locnNbr) {
        this.locnNbr = locnNbr;
    }

    public void setLgclLocnNbr(String lgclLocnNbr) {
        this.lgclLocnNbr = lgclLocnNbr;
    }

    public void setContainerBarcode(String containerBarcode) {
        this.containerBarcode = containerBarcode;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setKeyrec(String keyrec) {
        this.keyrec = keyrec;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public void setReplayFlag(String replayFlag) {
        this.replayFlag = replayFlag;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setLockCodes(Collection<String> lockCodes) {
        this.lockCodes = lockCodes;
    }

    public void setItems(Collection<IncomingItemDTO> items) {
        this.items = items;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IncomingMessageDTO)) {
            return false;
        }
        final IncomingMessageDTO other = (IncomingMessageDTO) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$application = this.getApplication();
        final Object other$application = other.getApplication();
        if (this$application == null ? other$application != null : !this$application.equals(other$application)) {
            return false;
        }
        final Object this$correlationID = this.getCorrelationID();
        final Object other$correlationID = other.getCorrelationID();
        if (this$correlationID == null ? other$correlationID != null : !this$correlationID.equals(other$correlationID)) {
            return false;
        }
        final Object this$eventName = this.getEventName();
        final Object other$eventName = other.getEventName();
        if (this$eventName == null ? other$eventName != null : !this$eventName.equals(other$eventName)) {
            return false;
        }
        final Object this$locnNbr = this.getLocnNbr();
        final Object other$locnNbr = other.getLocnNbr();
        if (this$locnNbr == null ? other$locnNbr != null : !this$locnNbr.equals(other$locnNbr)) {
            return false;
        }
        final Object this$lgclLocnNbr = this.getLgclLocnNbr();
        final Object other$lgclLocnNbr = other.getLgclLocnNbr();
        if (this$lgclLocnNbr == null ? other$lgclLocnNbr != null : !this$lgclLocnNbr.equals(other$lgclLocnNbr)) {
            return false;
        }
        final Object this$containerBarcode = this.getContainerBarcode();
        final Object other$containerBarcode = other.getContainerBarcode();
        if (this$containerBarcode == null ? other$containerBarcode != null : !this$containerBarcode.equals(other$containerBarcode)) {
            return false;
        }
        final Object this$asn = this.getAsn();
        final Object other$asn = other.getAsn();
        if (this$asn == null ? other$asn != null : !this$asn.equals(other$asn)) {
            return false;
        }
        final Object this$po = this.getPo();
        final Object other$po = other.getPo();
        if (this$po == null ? other$po != null : !this$po.equals(other$po)) {
            return false;
        }
        final Object this$receipt = this.getReceipt();
        final Object other$receipt = other.getReceipt();
        if (this$receipt == null ? other$receipt != null : !this$receipt.equals(other$receipt)) {
            return false;
        }
        final Object this$keyrec = this.getKeyrec();
        final Object other$keyrec = other.getKeyrec();
        if (this$keyrec == null ? other$keyrec != null : !this$keyrec.equals(other$keyrec)) {
            return false;
        }
        final Object this$suffix = this.getSuffix();
        final Object other$suffix = other.getSuffix();
        if (this$suffix == null ? other$suffix != null : !this$suffix.equals(other$suffix)) {
            return false;
        }
        final Object this$reason = this.getReason();
        final Object other$reason = other.getReason();
        if (this$reason == null ? other$reason != null : !this$reason.equals(other$reason)) {
            return false;
        }
        final Object this$receiptStatus = this.getReceiptStatus();
        final Object other$receiptStatus = other.getReceiptStatus();
        if (this$receiptStatus == null ? other$receiptStatus != null : !this$receiptStatus.equals(other$receiptStatus)) {
            return false;
        }
        final Object this$replayFlag = this.getReplayFlag();
        final Object other$replayFlag = other.getReplayFlag();
        if (this$replayFlag == null ? other$replayFlag != null : !this$replayFlag.equals(other$replayFlag)) {
            return false;
        }
        final Object this$orderSource = this.getOrderSource();
        final Object other$orderSource = other.getOrderSource();
        if (this$orderSource == null ? other$orderSource != null : !this$orderSource.equals(other$orderSource)) {
            return false;
        }
        final Object this$orderNumber = this.getOrderNumber();
        final Object other$orderNumber = other.getOrderNumber();
        if (this$orderNumber == null ? other$orderNumber != null : !this$orderNumber.equals(other$orderNumber)) {
            return false;
        }
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) {
            return false;
        }
        final Object this$lockCodes = this.getLockCodes();
        final Object other$lockCodes = other.getLockCodes();
        if (this$lockCodes == null ? other$lockCodes != null : !this$lockCodes.equals(other$lockCodes)) {
            return false;
        }
        final Object this$items = this.getItems();
        final Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IncomingMessageDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $application = this.getApplication();
        result = result * PRIME + ($application == null ? 43 : $application.hashCode());
        final Object $correlationID = this.getCorrelationID();
        result = result * PRIME + ($correlationID == null ? 43 : $correlationID.hashCode());
        final Object $eventName = this.getEventName();
        result = result * PRIME + ($eventName == null ? 43 : $eventName.hashCode());
        final Object $locnNbr = this.getLocnNbr();
        result = result * PRIME + ($locnNbr == null ? 43 : $locnNbr.hashCode());
        final Object $lgclLocnNbr = this.getLgclLocnNbr();
        result = result * PRIME + ($lgclLocnNbr == null ? 43 : $lgclLocnNbr.hashCode());
        final Object $containerBarcode = this.getContainerBarcode();
        result = result * PRIME + ($containerBarcode == null ? 43 : $containerBarcode.hashCode());
        final Object $asn = this.getAsn();
        result = result * PRIME + ($asn == null ? 43 : $asn.hashCode());
        final Object $po = this.getPo();
        result = result * PRIME + ($po == null ? 43 : $po.hashCode());
        final Object $receipt = this.getReceipt();
        result = result * PRIME + ($receipt == null ? 43 : $receipt.hashCode());
        final Object $keyrec = this.getKeyrec();
        result = result * PRIME + ($keyrec == null ? 43 : $keyrec.hashCode());
        final Object $suffix = this.getSuffix();
        result = result * PRIME + ($suffix == null ? 43 : $suffix.hashCode());
        final Object $reason = this.getReason();
        result = result * PRIME + ($reason == null ? 43 : $reason.hashCode());
        final Object $receiptStatus = this.getReceiptStatus();
        result = result * PRIME + ($receiptStatus == null ? 43 : $receiptStatus.hashCode());
        final Object $replayFlag = this.getReplayFlag();
        result = result * PRIME + ($replayFlag == null ? 43 : $replayFlag.hashCode());
        final Object $orderSource = this.getOrderSource();
        result = result * PRIME + ($orderSource == null ? 43 : $orderSource.hashCode());
        final Object $orderNumber = this.getOrderNumber();
        result = result * PRIME + ($orderNumber == null ? 43 : $orderNumber.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $lockCodes = this.getLockCodes();
        result = result * PRIME + ($lockCodes == null ? 43 : $lockCodes.hashCode());
        final Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "IncomingMessageDTO(application=" + this.getApplication() + ", correlationID=" + this.getCorrelationID() + ", eventName=" + this.getEventName() + ", locnNbr=" + this.getLocnNbr() + ", lgclLocnNbr=" + this.getLgclLocnNbr() + ", containerBarcode=" + this.getContainerBarcode() + ", asn=" + this.getAsn() + ", po=" + this.getPo() + ", receipt=" + this.getReceipt() + ", keyrec=" + this.getKeyrec() + ", suffix=" + this.getSuffix() + ", reason=" + this.getReason() + ", receiptStatus=" + this.getReceiptStatus() + ", replayFlag=" + this.getReplayFlag() + ", orderSource=" + this.getOrderSource() + ", orderNumber=" + this.getOrderNumber() + ", timestamp=" + this.getTimestamp() + ", lockCodes=" + this.getLockCodes() + ", items=" + this.getItems() + ")";
    }
}
