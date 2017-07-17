<%--
  Created by IntelliJ IDEA.
  User: leastmoney
  Date: 2017/7/3
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


    <c:set var="a" value=""></c:set>
    <c:forEach items="${_CURRENT_USER.roles }" var="role">
        <c:forEach items="${role.modules }" var="m">
                <c:if test="${fn:contains( m.cwhich,cwhich) }">
                    <c:if test="${m.ctype == 2}">
                         <c:if test="${fn:contains(a,m.name ) eq false}">
                                <c:set var="a" value="${a},${m.name}"/>
                                <li id="${m.ico}"><a href="${m.curl}" onclick="linkHighlighted(this)" target="main" id="aa_1">${m.name }</a></li>
                            </c:if>
                </c:if>
            </c:if>
        </c:forEach>
    </c:forEach>
