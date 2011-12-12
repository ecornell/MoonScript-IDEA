/*
 * Copyright 2010 Jon S Akhtar (Sylvanaar)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.eightbitmage.moonscript.lang.psi.impl.expressions;

import com.eightbitmage.moonscript.lang.lexer.LuaTokenTypes;
import com.eightbitmage.moonscript.lang.parser.LuaElementTypes;
import com.eightbitmage.moonscript.lang.psi.LuaPsiElement;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaBinaryExpression;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaExpression;
import com.eightbitmage.moonscript.lang.psi.visitor.LuaElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 12, 2010
 * Time: 11:37:52 PM
 */
public class LuaBinaryExpressionImpl extends LuaExpressionImpl implements LuaBinaryExpression {
    public LuaBinaryExpressionImpl(ASTNode node) {
        super(node);
    }

    @Override
    public LuaPsiElement getOperator() {
        return (LuaPsiElement) findChildByType(LuaElementTypes.BINARY_OP);
    }

    @Override
    public String toString() {
        try {
        return super.toString() + " ("  + getLeftExpression().getText() + ") " + getOperator().getText() + " (" + getRightExpression().getText() +  ")";
        } catch (Throwable unused) {}

        return "err";
    }

    @Override
    public LuaExpression getLeftExpression() {
        LuaExpression[] e = findChildrenByClass(LuaExpression.class);
        return  e.length>0?e[0]:null;
    }

    @Override
    public IElementType getOperationTokenType() {
        ASTNode child = getOperator().getNode().findChildByType(LuaTokenTypes.BINARY_OP_SET);
        return child!=null ? child.getElementType() : null;
    }

    @Override
    public LuaExpression getLeftOperand() {
        return getLeftExpression();
    }

    @Override
    public LuaExpression getRightOperand() {
        return getRightExpression();
    }

    @Override
    public LuaExpression getRightExpression() {
        LuaExpression[] e = findChildrenByClass(LuaExpression.class);
        return  e.length>1?e[1]:null;
    }
        @Override
    public void accept(LuaElementVisitor visitor) {
        visitor.visitBinaryExpression(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof LuaElementVisitor) {
            ((LuaElementVisitor) visitor).visitBinaryExpression(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
