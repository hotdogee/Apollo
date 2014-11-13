package org.gmod.gbol.simpleObject.generated;


import org.gmod.gbol.simpleObject.*; 

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TableInfo generated by hbm2java
 */
public abstract class AbstractTableInfo extends AbstractSimpleObject implements java.io.Serializable {


     private Integer tableInfoId;
     private String name;
     private String primaryKeyColumn;
     private int isView;
     private Integer viewOnTableId;
     private Integer superclassTableId;
     private int isUpdateable;
     private Date modificationDate;
     private Set<MAGEDocumentation> mageDocuments = new HashSet<MAGEDocumentation>(0);

    public AbstractTableInfo() {
    }

    
    public AbstractTableInfo(String name, int isView, int isUpdateable, Date modificationDate) {
        this.name = name;
        this.isView = isView;
        this.isUpdateable = isUpdateable;
        this.modificationDate = modificationDate;
    }
    public AbstractTableInfo(String name, String primaryKeyColumn, int isView, Integer viewOnTableId, Integer superclassTableId, int isUpdateable, Date modificationDate, Set<MAGEDocumentation> mageDocuments) {
       this.name = name;
       this.primaryKeyColumn = primaryKeyColumn;
       this.isView = isView;
       this.viewOnTableId = viewOnTableId;
       this.superclassTableId = superclassTableId;
       this.isUpdateable = isUpdateable;
       this.modificationDate = modificationDate;
       this.mageDocuments = mageDocuments;
    }
   
    public Integer getTableInfoId() {
        return this.tableInfoId;
    }
    
    public void setTableInfoId(Integer tableInfoId) {
        this.tableInfoId = tableInfoId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getPrimaryKeyColumn() {
        return this.primaryKeyColumn;
    }
    
    public void setPrimaryKeyColumn(String primaryKeyColumn) {
        this.primaryKeyColumn = primaryKeyColumn;
    }
    public int getIsView() {
        return this.isView;
    }
    
    public void setIsView(int isView) {
        this.isView = isView;
    }
    public Integer getViewOnTableId() {
        return this.viewOnTableId;
    }
    
    public void setViewOnTableId(Integer viewOnTableId) {
        this.viewOnTableId = viewOnTableId;
    }
    public Integer getSuperclassTableId() {
        return this.superclassTableId;
    }
    
    public void setSuperclassTableId(Integer superclassTableId) {
        this.superclassTableId = superclassTableId;
    }
    public int getIsUpdateable() {
        return this.isUpdateable;
    }
    
    public void setIsUpdateable(int isUpdateable) {
        this.isUpdateable = isUpdateable;
    }
    public Date getModificationDate() {
        return this.modificationDate;
    }
    
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    public Set<MAGEDocumentation> getMageDocuments() {
        return this.mageDocuments;
    }
    
    public void setMageDocuments(Set<MAGEDocumentation> mageDocuments) {
        this.mageDocuments = mageDocuments;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
         if ( (other == null ) ) return false;
         if ( !(other instanceof AbstractTableInfo) ) return false;
         AbstractTableInfo castOther = ( AbstractTableInfo ) other; 
         
         return ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         
         
         
         
         
         
         
         return result;
   }   

public AbstractTableInfo generateClone() {
    AbstractTableInfo cloned = new TableInfo(); 
           cloned.name = this.name;
           cloned.primaryKeyColumn = this.primaryKeyColumn;
           cloned.isView = this.isView;
           cloned.viewOnTableId = this.viewOnTableId;
           cloned.superclassTableId = this.superclassTableId;
           cloned.isUpdateable = this.isUpdateable;
           cloned.modificationDate = this.modificationDate;
           cloned.mageDocuments = this.mageDocuments;
    return cloned;
}


}

