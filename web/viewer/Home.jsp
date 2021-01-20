<%-- 
    Document   : Home
    Created on : Dec 5, 2020, 12:56:04 PM
    Author     : Jamie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">
    <%
        String userUrl = (String) session.getAttribute("folderUrl");
        String fullName = (String) session.getAttribute("fullName");
        String[] pages = (String[]) session.getAttribute("pages");
        String[] pagesIcons = (String[]) session.getAttribute("pagesIcons");
        String[] pagesDescription = (String[]) session.getAttribute("pagesDescription");
    %>
    <h3>You are currently logged in as <span style="font-weight: bold"><%=fullName%></span> </h3>
    <h4>What do you want to do?</h4>
    <ul id="home-page-tasks">
        <%
            for (int i = 0; i < pages.length; i++) {
                String url = userUrl + pages[i].trim().replace(" ", "") + ".jsp";%>
        <li>
            <div>
                <a href="<%=url%>">
                    <span class="icon"><i class="fas fa-<%=pagesIcons[i]%>"></i></span>
                    <span class="page-name"><%=pages[i]%></span>
                    <p><%=pagesDescription[i]%></p>

                </a> 
            </div>

        </li>
        <%  }%>
    </ul>
    
    
    <div class="our-team">
        <h1 style="text-align:center; color: #6838EA">Our Leaders</h1>
        
        <div class="team-members">
            <div class="member">
                <img src="https://imgur.com/WvW1Ws1.jpg" />
                <h3>Dr. Miranda Bailey </h3>
                <p class="member-rank">Chief of Surgery</p>
                <p class="member-quotes">"But At The End Of The Day, The Fact That We Show Up For Each Other, In Spite Of Our Differences, No Matter What We Believe, Is Reason Enough To Keep Believing."</p>
            </div>
            
            <div class="member">
                <img src="https://imgur.com/fDhjNkJ.jpg" />
                <h3>Dr. Meredith Grey</h3>
                <p class="member-rank">Head of General Surgery</p>
                <p class="member-quotes">"When there’s something you really want, fight for it – don’t give up no matter how hopeless it seems.</p>
            </div>
            
            <div class="member">
                <img src="https://imgur.com/unuXZmi.jpg" />
                <h3>Dr. Christina Yang</h3>
                <p class="member-rank">Director of Cardiothoracic Surgery</p>
                <p class="member-quotes">"Have some fire. Be unstoppable. Be a force of nature. Be better than anyone here, and don’t give a damn what anyone thinks."</p>
            </div>
            
            <div class="member">
                <img src="https://cdn1.edgedatg.com/aws/v2/abc/GreysAnatomy/person/736412/d94c8525cab2c70bdec9916d34349cec/579x579-Q90_d94c8525cab2c70bdec9916d34349cec.jpg" />
                <h3>Dr. Jackson Avery</h3>
                <p class="member-rank">Head of Neurosurgery</p>
                <p class="member-quotes">"The only way to fail is not to fight. So you fight until you can’t fight anymore"</p>
            </div>
        </div>
        
    </div>

</div>
<jsp:include page="/viewer/Footer.jsp"/>
