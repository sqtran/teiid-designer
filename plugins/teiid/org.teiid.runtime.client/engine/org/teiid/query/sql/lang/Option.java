/* Generated By:JJTree: Do not edit this line. Option.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.teiid.designer.annotation.Since;
import org.teiid.designer.annotation.Updated;
import org.teiid.designer.query.sql.lang.IOption;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.language.SQLConstants.Reserved;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidParser;
import org.teiid.query.sql.visitor.SQLStringVisitor;

/**
 *
 */
public class Option extends SimpleNode implements IOption<LanguageVisitor> {

    /**
     * Make Dep token
     */
    public final static String MAKEDEP = Reserved.MAKEDEP; 

    /**
     * Make Not Dep token
     */
    public final static String MAKENOTDEP = Reserved.MAKENOTDEP;

    /**
     * Optional token
     */
    public final static String OPTIONAL = "optional"; //$NON-NLS-1$

    public static class MakeDep {
        private Integer max;

        @Updated(version=Version.TEIID_8_12_4)
        private Boolean join;

        private ITeiidServerVersion teiidVersion;
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.join == null) ? 0 : this.join.hashCode());
            result = prime * result + ((this.max == null) ? 0 : this.max.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MakeDep other = (MakeDep)obj;
            if (this.join == null) {
                if (other.join != null)
                    return false;
            } else if (!this.join.equals(other.join))
                return false;
            if (this.max == null) {
                if (other.max != null)
                    return false;
            } else if (!this.max.equals(other.max))
                return false;
            return true;
        }

        public MakeDep(ITeiidServerVersion teiidVersion) {
            this.teiidVersion = teiidVersion;
        }
        
        @Override
        public String toString() {
            return new SQLStringVisitor(teiidVersion).appendMakeDepOptions(this).getSQLString();
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        @Updated(version=Version.TEIID_8_12_4)
        public boolean isJoin() {
            if (join == null)
                return false;

            return join;
        }

        @Since(Version.TEIID_8_12_4)
        public Boolean getJoin() {
            return join;
        }

        @Updated(version=Version.TEIID_8_12_4)
        public void setJoin(Boolean join) {
            this.join = join;
        }

        @Updated(version=Version.TEIID_8_12_4)
        public boolean isSimple() {
            return max == null && join == null;
        }
    }

    private List<String> makeDependentGroups;

    @Since(Version.TEIID_8_12_4)
    private List<String> makeIndependentGroups;

    @Since(Version.TEIID_8_5)
    private List<MakeDep> makeDependentOptions;

    @Since(Version.TEIID_8_12_4)
    private List<MakeDep> makeIndependentOptions;

    private List<String> makeNotDependentGroups;

    private List<String> noCacheGroups;

    private boolean noCache;

    /**
     * @param p
     * @param id
     */
    public Option(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Add group to make dependent
     * @param group Group to make dependent
     */
    public void addDependentGroup(String group) {
        addDependentGroup(group, new MakeDep(getTeiidVersion()));
    }

    public void addDependentGroup(String group, MakeDep makedep) {
        if (makedep == null) {
            return;
        }
        if(this.makeDependentGroups == null) {
            this.makeDependentGroups = new ArrayList<String>();
            this.makeDependentOptions = new ArrayList<MakeDep>();
        }
        this.makeDependentGroups.add(group);
        this.makeDependentOptions.add(makedep);
    }

    @Since(Version.TEIID_8_12_4)
    public void addIndependentGroup(String group, MakeDep makedep) {
        if (isLessThanTeiidVersion(Version.TEIID_8_12_4))
            return;

        if (makedep == null) {
            return;
        }
        if(this.makeIndependentGroups == null) {
            this.makeIndependentGroups = new ArrayList<String>();
            this.makeIndependentOptions = new ArrayList<MakeDep>();
        }
        this.makeIndependentGroups.add(group);    
        this.makeIndependentOptions.add(makedep);
    }

    /** 
     * Get all groups to make dependent
     * @return List of String defining groups to be made dependent, may be null if no groups
     */
    @Override
    public List<String> getDependentGroups() {
        return this.makeDependentGroups;
    }

    public List<MakeDep> getMakeDepOptions() {
        return this.makeDependentOptions;
    }

    @Since(Version.TEIID_8_12_4)
    public List<MakeDep> getMakeIndependentOptions() {
        if (isLessThanTeiidVersion(Version.TEIID_8_12_4))
            return Collections.emptyList();

        return makeIndependentOptions;
    }

    @Since(Version.TEIID_8_12_4)
    public List<String> getMakeIndependentGroups() {
        if (isLessThanTeiidVersion(Version.TEIID_8_12_4))
            return Collections.emptyList();

        return makeIndependentGroups;
    }

    /**
     * Add group to make dependent
     * @param group Group to make dependent
     */
    public void addNotDependentGroup(String group) {
        if(this.makeNotDependentGroups == null) {
            this.makeNotDependentGroups = new ArrayList<String>();
        }
        this.makeNotDependentGroups.add(group);    
    }
    
    /** 
     * Get all groups to make dependent
     * @return List of String defining groups to be made dependent, may be null if no groups
     */
    @Override
    public List<String> getNotDependentGroups() {
        return this.makeNotDependentGroups;
    }
    
    /**
     * Add group that overrides the default behavior of Materialized View feautre
     * to route the query to the primary virtual group transformation instead of 
     * the Materialized View transformation.
     * @param group Group that overrides the default behavior of Materialized View
     */
    public void addNoCacheGroup(String group) {
        if(this.noCacheGroups == null) {
            this.noCacheGroups = new ArrayList<String>();
        }
        this.noCacheGroups.add(group);    
    }
    
    /** 
     * Get all groups that override the default behavior of Materialized View feautre
     * to route the query to the primary virtual group transformation instead of 
     * the Materialized View transformation.
     * @return List of String defining groups that overrides the default behavior of 
     * Materialized View, may be null if there are no groups
     */
    @Override
    public List<String> getNoCacheGroups() {
        return this.noCacheGroups;
    }
    
    @Override
    public boolean isNoCache() {
        return noCache;
    }

    /**
     * @param noCache
     */
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.makeDependentGroups == null) ? 0 : this.makeDependentGroups.hashCode());
        result = prime * result + ((this.makeDependentOptions == null) ? 0 : this.makeDependentOptions.hashCode());

        if (!isLessThanTeiidVersion(Version.TEIID_8_12_4)) {
            result = prime * result + ((this.makeIndependentGroups == null) ? 0 : this.makeIndependentGroups.hashCode());
            result = prime * result + ((this.makeIndependentOptions == null) ? 0 : this.makeIndependentOptions.hashCode());
        }

        result = prime * result + ((this.makeNotDependentGroups == null) ? 0 : this.makeNotDependentGroups.hashCode());
        result = prime * result + (this.noCache ? 1231 : 1237);
        result = prime * result + ((this.noCacheGroups == null) ? 0 : this.noCacheGroups.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Option other = (Option)obj;
        if (this.makeDependentGroups == null) {
            if (other.makeDependentGroups != null)
                return false;
        } else if (!this.makeDependentGroups.equals(other.makeDependentGroups))
            return false;
        if (this.makeDependentOptions == null) {
            if (other.makeDependentOptions != null)
                return false;
        } else if (!this.makeDependentOptions.equals(other.makeDependentOptions))
            return false;

        if (!isLessThanTeiidVersion(Version.TEIID_8_12_4)) {
            if (this.makeIndependentGroups == null) {
                if (other.makeIndependentGroups != null)
                    return false;
            } else if (!this.makeIndependentGroups.equals(other.makeIndependentGroups))
                return false;
            if (this.makeIndependentOptions == null) {
                if (other.makeIndependentOptions != null)
                    return false;
            } else if (!this.makeIndependentOptions.equals(other.makeIndependentOptions))
                return false;
        }

        if (this.makeNotDependentGroups == null) {
            if (other.makeNotDependentGroups != null)
                return false;
        } else if (!this.makeNotDependentGroups.equals(other.makeNotDependentGroups))
            return false;
        if (this.noCache != other.noCache)
            return false;
        if (this.noCacheGroups == null) {
            if (other.noCacheGroups != null)
                return false;
        } else if (!this.noCacheGroups.equals(other.noCacheGroups))
            return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Option clone() {
        Option clone = new Option(this.parser, this.id);

        clone.setNoCache(isNoCache());

        if(this.makeDependentGroups != null) {
            clone.makeDependentGroups = new ArrayList<String>(this.makeDependentGroups);
            clone.makeDependentOptions = new ArrayList<MakeDep>(this.makeDependentOptions);
        }

        if(this.makeIndependentGroups != null) {
            clone.makeIndependentGroups = new ArrayList<String>(this.makeIndependentGroups);
            clone.makeIndependentOptions = new ArrayList<MakeDep>(this.makeIndependentOptions);
        }

        if(getNotDependentGroups() != null) {
            clone.makeNotDependentGroups = new ArrayList<String>(getNotDependentGroups());
        }
            
        if(getNoCacheGroups() != null) {
            clone.noCacheGroups = new ArrayList<String>(getNoCacheGroups());
        }

        return clone;
    }

}
/* JavaCC - OriginalChecksum=a564d0b56868fc308d004c702396106a (do not edit this line) */
