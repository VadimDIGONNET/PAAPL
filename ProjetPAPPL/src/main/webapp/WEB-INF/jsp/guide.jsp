<%-- 
    Document   : guide
    Created on : 23 Mar 2026, 11:50:13
    Author     : selli
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guide Utilisateur</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
        
        <style>
            html, body {
                height: 100%;
                margin: 0;
            }

            .main-wrapper {
                display: flex;
                flex-direction: column;
                justify-content: center; 
                align-items: center;     
                min-height: 100vh;
                padding: 40px;
                box-sizing: border-box;
            }

            .titre-guide {
                color: #0066CC; 
                font-size: 4.5rem; 
                font-weight: 700;
                text-transform: uppercase;
                letter-spacing: 2px;
                margin: 0;
                text-align: center;
            }

            .separator-guide {
                border-top: 6px solid #FFD700; 
                width: 150px;
                margin: 20px 0 40px 0;
            }

            .content-container {
                max-width: 900px;
                width: 100%;
                background-color: white;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            }

            .section-title {
                font-size: 1.5rem;
                color: #0066CC;
                margin-top: 30px;
                border-left: 5px solid #FFD700;
                padding-left: 15px;
            }

            ul {
                line-height: 1.8;
                color: #555;
            }
        </style>
    </head>
    <body>
        <div class="main-wrapper">

            <h1 class="titre-guide">Guide d'Utilisation</h1>

            <div class="separator-guide"></div>

            <div class="content-container">
                <h2 class="section-title">Flux de travail impératif</h2>
                    <p>Pour garantir la cohérence des données dans l'application PAPPL, respectez cet ordre :</p>
                    <ul>
                        <li><strong>1. Entreprises :</strong> Enregistrez d'abord le partenaire.</li>
                        <li><strong>2. Sujets :</strong> Créez le projet en le liant à l'entreprise.</li>
                        <li><strong>3. Intervenants :</strong> Ajoutez enfin les élèves et tuteurs sur le sujet créé.</li>
                    </ul>                                
                <h3>Ordre de saisie impératif</h3>
                    <p>Pour garantir la liaison automatique entre les données, suivez cet ordre précis :</p>
                    <div class="row text-center mt-3">
                        <div class="col-md-3">
                            <div class="p-3 border rounded bg-light mb-2"><strong>1. Entreprises (partenaires)</strong></div>
                            <div class="p-3 border rounded bg-light mb-2"><strong>2. Sujets</strong></div>
                            <div class="p-3 border rounded bg-light mb-2"><strong>3. Intervenants (élèves et tuteurs)</strong></div>
                        </div>
                    </div>
                    <div class="alert alert-info mt-4">
                        <br/> Note : Un élève ou un tuteur doit obligatoirement être associé à un Numéro de Sujet existant lors de son ajout.
                    </div>

                           
                <h3>Import Word (.docx) </h3>
                    <ul>
                        <li>Utilisé uniquement pour les Sujets.</li>
                        <li>Le système extrait automatiquement le titre, le descriptif et le contact.</li>
                        <li>Évitez les fichiers avec des macros ou des protections par mot de passe.</li>
                    </ul>
                
                <h3>Import CSV (.csv)</h3>
                    <ul>
                        <li>Utilisé pour les Élèves et Tuteurs.</li>
                        <li>Séparateur : Point-virgule (<code>;</code>).</li>
                        <li>Encodage : UTF-8 (impératif pour les accents).</li>
                        <li>La colonne <code>ID_SUJET</code> doit contenir le numéro ID du projet.</li>
                    </ul>

                <div class="tab-pane fade" id="filtres" role="tabpanel">
                    <h3>Gestion de l'Année Universitaire</h3>
                    <p>L'application fonctionne par cycles annuels :</p>
                    <ul>
                        <li><strong>Filtrage :</strong> Si l'année 2026 est sélectionnée, seuls les projets et élèves de 2026 sont visibles.</li>
                        <li><strong>Changement d'année :</strong> Pour consulter des archives ou préparer l'année suivante, utilisez le sélecteur en haut de page.</li>
                        <li><strong>Export :</strong> Le bouton "Générer l'Excel" n'exporte que les données affichées selon le filtre actif.</li>
                    </ul>
                </div>

                <div class="tab-pane fade" id="faq" role="tabpanel">
                    <div class="accordion" id="faqAccordion">
                        <div class="accordion-item">
                            <h2 class="accordion-header">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#q1">
                                    Pourquoi l'import d'un élève échoue ?
                                </button>
                            </h2>
                            <div id="q1" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                                <div class="accordion-body">
                                    Vérifiez que le Numéro de Sujet indiqué dans votre CSV existe bien dans l'onglet "Sujets". Si le sujet n'existe pas, le système rejette l'élève pour éviter les données orphelines.
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#q2">
                                    Mes données ont disparu !
                                </button>
                            </h2>
                            <div id="q2" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                                <div class="accordion-body">
                                    Elles ne sont probablement que masquées. Vérifiez l'année sélectionnée dans le bandeau supérieur. Cliquez sur "Supprimer le filtre" pour tout voir.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
