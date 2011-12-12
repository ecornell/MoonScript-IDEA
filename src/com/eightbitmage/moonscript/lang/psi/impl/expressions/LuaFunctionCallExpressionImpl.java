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

import com.eightbitmage.moonscript.lang.psi.LuaReferenceElement;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaExpressionList;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaFunctionArguments;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaFunctionCallExpression;
import com.eightbitmage.moonscript.lang.psi.visitor.LuaElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNamedElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Aug 28, 2010
 * Time: 10:04:11 AM
 */
public class LuaFunctionCallExpressionImpl extends LuaExpressionImpl implements LuaFunctionCallExpression, PsiNamedElement {
    public LuaFunctionCallExpressionImpl(ASTNode node) {
        super(node);
    }

    public String toString() {
        return "Call: " + getNameRaw();
    }

            @Override
    public void accept(LuaElementVisitor visitor) {
        visitor.visitFunctionCall(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof LuaElementVisitor) {
            ((LuaElementVisitor) visitor).visitFunctionCall(this);
        } else {
            visitor.visitElement(this);
        }
    }

    public String getNameRaw() {
        LuaReferenceElement e = findChildByClass(LuaReferenceElement.class);

        if (e != null) return e.getText();

        return null;
    }

    @Override
    public String getName() {
        LuaReferenceElement e = findChildByClass(LuaReferenceElement.class);

        if (e != null) return e.getName();

        return null;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Nullable
    public LuaExpressionList getArgumentList() {
        return findChildByClass(LuaFunctionArguments.class).getExpressions();
    }

    @Override
    public LuaReferenceElement getFunctionNameElement() {
        return findChildByClass(LuaReferenceElement.class);
    }
}