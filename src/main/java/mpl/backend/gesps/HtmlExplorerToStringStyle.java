package mpl.backend.gesps;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marilia Portela on 19/03/2017.
 */
public class HtmlExplorerToStringStyle extends ToStringStyle {

    HtmlExplorerToStringStyle(){
        this.setUseClassName(false);
        this.setUseIdentityHashCode(false);
        this.setUseFieldNames(false);
        this.setContentStart("");
        this.setContentEnd("");
    }

    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof Date) {
            value = new SimpleDateFormat("dd-MM-yyyy").format(value);
        }
        buffer.append(value);
    }

}
