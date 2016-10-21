/* Generated By:JJTree: Do not edit this line. CommandStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.proc;

import org.teiid.designer.query.sql.proc.ICommandStatement;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.sql.lang.Command;
import org.teiid.query.sql.lang.SubqueryContainer;

/**
 *
 */
public class CommandStatement extends Statement
    implements SubqueryContainer<Command>, ICommandStatement<LanguageVisitor, Command> {

    // the command this statement represents
    private Command command;

    private boolean returnable = true;

    /**
     * @param p
     * @param id
     */
    public CommandStatement(ITeiidServerVersion p, int id) {
        super(p, id);
    }

    /**
     * Return the type for this statement, this is one of the types
     * defined on the statement object.
     * @return The statement type
     */
    @Override
    public StatementType getType() {
        return StatementType.TYPE_COMMAND;
    }

    /**
     * Get the command on this statement.
     * @return The <code>Command</code> on this statement
     */
    @Override
    public Command getCommand() {
        return command; 
    }

    /**
     * @param command
     */
    @Override
    public void setCommand(Command command){
        this.command = command;
    }

    /**
     * @return returnable flag
     */
    public boolean isReturnable() {
        return returnable;
    }
    
    /**
     * @param returnable
     */
    public void setReturnable(boolean returnable) {
        this.returnable = returnable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.command == null) ? 0 : this.command.hashCode());
        result = prime * result + (this.returnable ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        CommandStatement other = (CommandStatement)obj;
        if (this.command == null) {
            if (other.command != null) return false;
        } else if (!this.command.equals(other.command)) return false;
        if (this.returnable != other.returnable) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public CommandStatement clone() {
        CommandStatement clone = new CommandStatement(getTeiidVersion(), this.id);

        clone.setReturnable(isReturnable());
        if(getCommand() != null)
            clone.setCommand(getCommand().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=62f9df6ce048861851414036eed990e3 (do not edit this line) */
