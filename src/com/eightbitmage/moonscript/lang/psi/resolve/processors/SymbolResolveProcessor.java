/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eightbitmage.moonscript.lang.psi.resolve.processors;

import com.eightbitmage.moonscript.lang.luadoc.psi.api.LuaDocSymbolReference;
import com.eightbitmage.moonscript.lang.psi.LuaReferenceElement;
import com.eightbitmage.moonscript.lang.psi.impl.symbols.LuaCompoundReferenceElementImpl;
import com.eightbitmage.moonscript.lang.psi.resolve.LuaResolveResultImpl;
import com.eightbitmage.moonscript.lang.psi.symbols.LuaGlobal;
import com.eightbitmage.moonscript.lang.psi.symbols.LuaSymbol;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;

import java.util.HashSet;
import java.util.Set;


/**
 * @author ilyas
 */
public class SymbolResolveProcessor extends ResolveProcessor {

  private final Set<PsiElement> myProcessedElements = new HashSet<PsiElement>();
  private final PsiElement myPlace;
  private final boolean incompleteCode;


  public SymbolResolveProcessor(String myName, PsiElement myPlace, boolean incompleteCode) {
    super(myName);
    this.myPlace = myPlace;
    this.incompleteCode = incompleteCode;

  }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    private boolean filter = true;




  public boolean execute(PsiElement element, ResolveState resolveState) {
  
    if (element instanceof LuaSymbol && !myProcessedElements.contains(element)) {
      LuaSymbol namedElement = (LuaSymbol) element;
      boolean isAccessible = isAccessible(namedElement);
      if (!filter || isAccessible)
          myCandidates.add(new LuaResolveResultImpl(namedElement, true));
      myProcessedElements.add(namedElement);
      return !filter || !isAccessible || ((PsiReference)myPlace).getElement() instanceof LuaGlobal;
    }

    return true;
  }

  /*
  todo: add ElementClassHints
   */
  public <T> T getHint(Key<T> hintKey) {
//    if (hintKey == NameHint.KEY && myName != null) {
//      return (T) this;
//    }

    return null;
  }

  public PsiElement getPlace() {
    return myPlace;
  }

  public String getName(ResolveState resolveState) {
    return myName;
  }

//  public boolean shouldProcess(DeclaractionKind kind) {
//    return true;
//  }

    protected boolean isAccessible(LuaSymbol namedElement) {
        if (myName == null) return true;

        if (myPlace instanceof LuaCompoundReferenceElementImpl) {
            return myName.equals(namedElement.getName());
        } else if (myPlace instanceof LuaDocSymbolReference) {
            return myName.equals(namedElement.getName());
        } else if (myPlace instanceof LuaReferenceElement) {
            return myName.equals(namedElement.getName()) && namedElement.isSameKind((LuaSymbol) ((LuaReferenceElement) myPlace).getElement());
        }

        return myName.equals(namedElement.getName());
    }
}