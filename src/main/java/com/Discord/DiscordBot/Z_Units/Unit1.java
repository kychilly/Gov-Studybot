package com.Discord.DiscordBot.Z_Units;

import com.Discord.DiscordBot.Units.Question;

import java.util.ArrayList;
import java.util.HashMap;


import static com.Discord.DiscordBot.Units.QuestionBank.unit1Questions;


public class Unit1 {


    public static int numUnit1Questions;


    public static void initializeUnit1Questions() {
        // AP Gov Unit 1: Foundations of American Democracy


        unit1Questions.add(new Question(
                "Which constitutional principle is most directly reflected in the phrase 'We the People' in the Preamble?",
                "Popular sovereignty", "Federalism", "Checks and balances", "Judicial review",
                "A", 1, 1, "easy"));


        unit1Questions.add(new Question(
                "What was the most significant weakness of the Articles of Confederation that led to the Constitutional Convention?",
                "The national government had no power to tax", "An overly powerful executive branch",
                "A federal judiciary that could strike down state laws", "Proportional representation in a unicameral Congress",
                "A", 1, 2, "easy"));


        unit1Questions.add(new Question(
                "The Great (Connecticut) Compromise resolved the dispute over which issue?",
                "Representation of states in the national legislature", "Slavery in the territories",
                "Addition of a bill of rights", "Selection of the president",
                "A", 1, 3, "easy"));


        unit1Questions.add(new Question(
                "The Three-Fifths Compromise addressed which question during the Constitutional Convention?",
                "How to count enslaved persons for representation and taxation", "Whether to end the importation of enslaved persons immediately",
                "Whether states could nullify federal laws", "How to elect the president",
                "A", 1, 4, "easy"));


        unit1Questions.add(new Question(
                "Which statement best captures Madison’s argument in Federalist No. 10?",
                "Small republics best control factions through direct democracy",
                "A large republic dilutes the effects of factions via competition and representation",
                "Political parties are unconstitutional and dangerous",
                "Judicial review is necessary to check factions",
                "B", 1, 5, "medium"));


        unit1Questions.add(new Question(
                "According to Brutus No. 1, why would the proposed Constitution threaten liberty?",
                "It lacked a bill of rights and created a unicameral legislature",
                "The Necessary and Proper and Supremacy Clauses would destroy state authority in a large republic",
                "It made the executive too weak to protect rights",
                "It gave the judiciary power over foreign policy",
                "B", 1, 6, "medium"));


        unit1Questions.add(new Question(
                "Which principle gives each branch of the federal government some control over the others?",
                "Popular sovereignty", "Federalism", "Checks and balances", "Limited government",
                "C", 1, 7, "easy"));


        unit1Questions.add(new Question(
                "The power to coin money is an example of which type of power?",
                "Enumerated power", "Reserved power", "Concurrent power", "Inherent state power",
                "A", 1, 8, "easy"));


        unit1Questions.add(new Question(
                "Which amendment reserves powers not delegated to the national government to the states or the people?",
                "First Amendment", "Fifth Amendment", "Tenth Amendment", "Fourteenth Amendment",
                "C", 1, 9, "easy"));


        unit1Questions.add(new Question(
                "Which constitutional clause is most associated with the creation of implied powers for Congress?",
                "Supremacy Clause", "Necessary and Proper (Elastic) Clause",
                "Full Faith and Credit Clause", "Establishment Clause",
                "B", 1, 10, "easy"));


        unit1Questions.add(new Question(
                "Which clause establishes that valid national laws supersede conflicting state laws?",
                "Supremacy Clause", "Commerce Clause", "Privileges and Immunities Clause", "Free Exercise Clause",
                "A", 1, 11, "easy"));


        unit1Questions.add(new Question(
                "Which principle was affirmed in McCulloch v. Maryland (1819)?",
                "National supremacy and the legitimacy of implied powers", "Judicial review of executive orders",
                "Absolute state sovereignty over banking", "Prior restraint on the press is unconstitutional",
                "A", 1, 12, "medium"));


        unit1Questions.add(new Question(
                "United States v. Lopez (1995) primarily limited Congress’s authority under which constitutional provision?",
                "Necessary and Proper Clause", "Supremacy Clause", "Commerce Clause", "Establishment Clause",
                "C", 1, 13, "medium"));


        unit1Questions.add(new Question(
                "A system in which national and state governments remain supreme within their own spheres—often called 'layer-cake federalism'—is known as",
                "Cooperative federalism", "Dual federalism", "Fiscal federalism", "Unitary federalism",
                "B", 1, 14, "medium"));


        unit1Questions.add(new Question(
                "Federal grants given to states for a narrow, specific purpose with conditions attached are",
                "Block grants", "Categorical grants", "Revenue sharing", "Unfunded mandates",
                "B", 1, 15, "easy"));


        unit1Questions.add(new Question(
                "A federal requirement imposed on states without providing funds to implement it is known as a(n)",
                "Unfunded mandate", "Block grant", "Concurrent power", "Executive agreement",
                "A", 1, 16, "easy"));


        unit1Questions.add(new Question(
                "Which scenario best illustrates federalism?",
                "The president vetoes a bill passed by Congress",
                "The Supreme Court strikes down a federal statute",
                "States administer and run elections for federal offices",
                "Congress declares war on another nation",
                "C", 1, 17, "easy"));


        unit1Questions.add(new Question(
                "What is the most commonly used method for formally amending the Constitution?",
                "Proposed by a national convention; ratified by state conventions",
                "Proposed by 2/3 of state legislatures; ratified by 3/4 of state legislatures",
                "Proposed by 2/3 of both houses of Congress; ratified by 3/4 of state legislatures",
                "Proposed by a simple majority in Congress; ratified by a presidential signature",
                "C", 1, 18, "medium"));


        unit1Questions.add(new Question(
                "The primary purpose of checks and balances is to",
                "Guarantee states equal representation in Congress",
                "Centralize power in the national government",
                "Prevent any one branch from becoming too powerful",
                "Ensure political parties share power equally",
                "C", 1, 19, "easy"));


        unit1Questions.add(new Question(
                "Which founding document most directly argues for separation of powers and checks and balances?",
                "Federalist No. 10", "Brutus No. 1", "Federalist No. 51", "The Declaration of Independence",
                "C", 1, 20, "easy"));


        unit1Questions.add(new Question(
                "According to Madison, what is the best way to control the effects of factions?",
                "Ban interest groups to prevent faction formation",
                "Create a parliamentary system with a unitary executive",
                "Extend the sphere to create a large republic with many competing interests",
                "Allow states to nullify national laws they oppose",
                "C", 1, 21, "medium"));


        unit1Questions.add(new Question(
                "Which of the following is a concurrent power shared by national and state governments?",
                "Issuing driver’s licenses", "Declaring war",
                "Levying and collecting income taxes", "Negotiating treaties",
                "C", 1, 22, "easy"));


        unit1Questions.add(new Question(
                "How did Shays' Rebellion influence the framing of the Constitution?",
                "It proved the need for a bill of rights to protect speech",
                "It showed that a stronger national government was needed to maintain order",
                "It led to the direct election of senators",
                "It resulted in the immediate abolition of slavery in the North",
                "B", 1, 23, "medium"));


        unit1Questions.add(new Question(
                "Which plan at the Constitutional Convention called for representation based on state population?",
                "New Jersey Plan", "Virginia Plan", "Connecticut Compromise", "Albany Plan",
                "B", 1, 24, "easy"));


        unit1Questions.add(new Question(
                "Congress offers states increased highway funds if they adopt a 0.08 BAC standard for DUI. This is an example of",
                "Revenue sharing", "A conditional categorical grant", "Judicial review", "State nullification",
                "B", 1, 25, "hard"));




        unit1Questions.add(new Question(
                "Republicanism, as embraced by the Framers, is best defined as",
                "Direct citizen participation in national policy making",
                "Government based on the consent of the governed through elected representatives",
                "Rule by an elite hereditary class",
                "A theocratic system grounded in religious law",
                "B", 1, 26, "easy"));


        unit1Questions.add(new Question(
                "Which Enlightenment idea most influenced Jefferson’s argument in the Declaration of Independence?",
                "Divine Right of Kings",
                "Natural rights and the social contract",
                "Feudal obligations to the sovereign",
                "Mercantilism as national policy",
                "B", 1, 27, "easy"));


        unit1Questions.add(new Question(
                "Primary purpose of the Declaration of Independence?",
                "Design a new national government",
                "List grievances and justify separation from Britain",
                "Create a bill of rights",
                "Outline the war strategy against Britain",
                "B", 1, 28, "easy"));


        unit1Questions.add(new Question(
                "Which feature of the Articles of Confederation most hindered national policymaking?",
                "A strong independent judiciary",
                "Requirement of unanimous consent to amend the Articles",
                "Direct taxation of citizens by Congress",
                "A powerful presidency with veto power",
                "B", 1, 29, "medium"));


        unit1Questions.add(new Question(
                "Under the Articles of Confederation, Congress could",
                "Regulate interstate commerce",
                "Maintain a standing army without state approval",
                "Conduct foreign diplomacy and make treaties",
                "Levy income taxes directly on citizens",
                "C", 1, 30, "medium"));


        unit1Questions.add(new Question(
                "The Virginia Plan proposed",
                "Equal representation of states in a unicameral legislature",
                "Representation based on population in a bicameral legislature",
                "Selection of the executive by state legislatures only",
                "A national court with judges elected by the people",
                "B", 1, 31, "easy"));


        unit1Questions.add(new Question(
                "The New Jersey Plan was chiefly favored by",
                "Large, populous states",
                "States with major ports",
                "Smaller states seeking equal representation",
                "Southern slaveholding states only",
                "C", 1, 32, "easy"));


        unit1Questions.add(new Question(
                "Bicameralism primarily serves what constitutional goal?",
                "Speeding up the legislative process",
                "Ensuring popular sovereignty is ignored",
                "Checking momentary majorities by requiring concurrence of two chambers",
                "Eliminating the need for committees",
                "C", 1, 33, "medium"));


        unit1Questions.add(new Question(
                "Which compromise delayed congressional action on the international slave trade until 1808?",
                "Missouri Compromise",
                "Compromise on Importation of Slaves",
                "Northwest Ordinance",
                "Compromise of 1850",
                "B", 1, 34, "medium"));


        unit1Questions.add(new Question(
                "Ratification of the Constitution required approval by",
                "A unanimous vote of state legislatures",
                "Nine of thirteen state ratifying conventions",
                "A national referendum",
                "Two-thirds of both houses of the Confederation Congress",
                "B", 1, 35, "easy"));


        unit1Questions.add(new Question(
                "Anti-Federalists opposed the new Constitution mainly because they",
                "Preferred a unitary national government",
                "Feared loss of state sovereignty and wanted explicit protections for individual rights",
                "Believed monarchy would be more efficient",
                "Wanted proportional representation in the Senate",
                "B", 1, 36, "easy"));


        unit1Questions.add(new Question(
                "Which document most strongly argues that liberty is safest in a small, homogeneous republic?",
                "Federalist No. 10",
                "Brutus No. 1",
                "Federalist No. 51",
                "The Declaration of Independence",
                "B", 1, 37, "medium"));


        unit1Questions.add(new Question(
                "“Ambition must be made to counteract ambition” summarizes the logic of",
                "Brutus No. 1",
                "Federalist No. 51",
                "Federalist No. 70",
                "Federalist No. 78",
                "B", 1, 38, "easy"));


        unit1Questions.add(new Question(
                "The constitutional prohibition of bills of attainder means Congress cannot",
                "Pass a law that retroactively criminalizes prior behavior",
                "Punish a person or group without a judicial trial",
                "Suspend the writ of habeas corpus during peace",
                "Establish a national church",
                "B", 1, 39, "medium"));


        unit1Questions.add(new Question(
                "An ex post facto law is one that",
                "Provides funds to states after they implement a program",
                "Declares an act illegal after it was committed",
                "Requires police to obtain a warrant before arrest",
                "Transfers powers from states to the national government",
                "B", 1, 40, "medium"));


        unit1Questions.add(new Question(
                "Which clause requires states to respect the civil judgments and records of other states?",
                "Privileges and Immunities Clause",
                "Full Faith and Credit Clause",
                "Supremacy Clause",
                "Establishment Clause",
                "B", 1, 41, "easy"));


        unit1Questions.add(new Question(
                "A resident of State A who travels to State B must generally be treated similarly to citizens of State B due to the",
                "Necessary and Proper Clause",
                "Privileges and Immunities Clause",
                "Takings Clause",
                "Free Exercise Clause",
                "B", 1, 42, "medium"));


        unit1Questions.add(new Question(
                "Extradition in Article IV means that",
                "States must return fugitives charged with crimes to the state where the crime occurred",
                "States must share all tax revenue with the federal government",
                "Citizens may vote in any state’s elections",
                "States may nullify federal criminal laws",
                "A", 1, 43, "medium"));


        unit1Questions.add(new Question(
                "Which power is RESERVED to the states?",
                "Coining money",
                "Regulating marriage and divorce",
                "Declaring war",
                "Negotiating treaties",
                "B", 1, 44, "easy"));


        unit1Questions.add(new Question(
                "Which is an example of a CONCURRENT power?",
                "Issuing passports",
                "Taxation",
                "Admitting new states",
                "Regulating foreign commerce",
                "B", 1, 45, "easy"));


        unit1Questions.add(new Question(
                "The concept that the national government and state governments are sovereign in different spheres is called",
                "Cooperative federalism",
                "Unitary government",
                "Dual federalism",
                "Parliamentary federalism",
                "C", 1, 46, "medium"));


        unit1Questions.add(new Question(
                "Cooperative (\"marble-cake\") federalism grew most notably during which period?",
                "The Articles of Confederation period",
                "The New Deal era",
                "The Era of Good Feelings",
                "The Progressive Era pre-1900",
                "B", 1, 47, "medium"));


        unit1Questions.add(new Question(
                "Block grants are different from categorical grants in that block grants",
                "Are larger and must be spent on a narrowly defined project",
                "Provide states with broad discretion within a general policy area",
                "Are only for emergencies",
                "Can only be used for education",
                "B", 1, 48, "easy"));


        unit1Questions.add(new Question(
                "A federal requirement that states undertake an action without funding is a(n)",
                "Revenue share",
                "Unfunded mandate",
                "Project grant",
                "Formula grant",
                "B", 1, 49, "easy"));


        unit1Questions.add(new Question(
                "When Congress uses its spending power to encourage states to adopt a policy by threatening to withhold funds, it is using",
                "Judicial review",
                "Crossover sanctions or conditions of aid",
                "Executive privilege",
                "Preemption under the Supremacy Clause",
                "B", 1, 50, "medium"));


        unit1Questions.add(new Question(
                "Which pair correctly matches the branch with a key constitutional power?",
                "Legislative—interpreting laws",
                "Executive—declaring war",
                "Judicial—resolving cases and controversies",
                "Judicial—vetoing legislation",
                "C", 1, 51, "easy"));


        unit1Questions.add(new Question(
                "Which power is expressly delegated (enumerated) to Congress?",
                "Conducting elections",
                "Establishing post offices",
                "Issuing professional licenses",
                "Regulating intrastate commerce exclusively",
                "B", 1, 52, "easy"));


        unit1Questions.add(new Question(
                "The Necessary and Proper Clause has most often been used to",
                "Limit Congress to powers listed verbatim in Article I",
                "Expand federal power by creating implied powers",
                "Prevent the creation of national institutions like a bank",
                "Transfer powers to the states",
                "B", 1, 53, "medium"));


        unit1Questions.add(new Question(
                "In McCulloch v. Maryland, the Court held that",
                "States could tax federal instruments at their discretion",
                "Congress lacked authority to create a national bank",
                "Implied powers allow Congress to create a national bank; states cannot tax it",
                "The Tenth Amendment nullifies the Supremacy Clause",
                "C", 1, 54, "medium"));


        unit1Questions.add(new Question(
                "United States v. Lopez limited Congress by ruling that gun possession near schools",
                "Always affects interstate commerce",
                "Is purely local and exceeded Congress’s Commerce Clause authority",
                "Is protected by the First Amendment",
                "Must be regulated by federal agencies only",
                "B", 1, 55, "hard"));


        unit1Questions.add(new Question(
                "Which of the following best describes a federal system?",
                "All powers are vested in the national government",
                "All sovereignty rests with regional governments",
                "Power is constitutionally divided between national and state governments",
                "Courts make all policy decisions",
                "C", 1, 56, "easy"));


        unit1Questions.add(new Question(
                "The 'laboratories of democracy' idea refers to",
                "Federal courts testing new constitutional theories",
                "States experimenting with policy solutions that can be adopted nationally",
                "Congressional committees drafting experimental bills",
                "The president’s cabinet piloting regulations",
                "B", 1, 57, "medium"));


        unit1Questions.add(new Question(
                "Which is the BEST example of a reserved police power of the states?",
                "Issuing patents for inventions",
                "Setting speed limits and criminal penalties",
                "Regulating tariffs on imports",
                "Minting currency",
                "B", 1, 58, "easy"));


        unit1Questions.add(new Question(
                "Revenue sharing (1970s) differed from grants-in-aid because it",
                "Required detailed program plans and matching funds",
                "Gave states money with virtually no strings attached",
                "Was limited to disaster relief",
                "Was constitutionally required by Article I",
                "B", 1, 59, "medium"));


        unit1Questions.add(new Question(
                "Which is TRUE of interstate compacts?",
                "They are prohibited by the Constitution",
                "They require approval by Congress when affecting federal interests",
                "They allow states to nullify federal statutes",
                "They can only be made with foreign nations",
                "B", 1, 60, "medium"));


        unit1Questions.add(new Question(
                "The Guarantee Clause in Article IV promises each state a",
                "Parliamentary system",
                "Direct democracy",
                "Republican form of government",
                "Confederate arrangement",
                "C", 1, 61, "easy"));


        unit1Questions.add(new Question(
                "Which constitutional feature MOST directly protects against majority tyranny?",
                "A unitary executive",
                "Bicameral legislature and staggered institutions",
                "National referenda",
                "Judges elected for short terms",
                "B", 1, 62, "medium"));


        unit1Questions.add(new Question(
                "Why did adding a Bill of Rights become politically necessary for ratification?",
                "It guaranteed equal representation of states",
                "It addressed Anti-Federalist concerns about individual liberties under a stronger national government",
                "It created a unicameral Congress",
                "It abolished slavery nationwide",
                "B", 1, 63, "easy"));


        unit1Questions.add(new Question(
                "Which amendment most clearly embodies federalism by reserving unspecified powers to the states or the people?",
                "Ninth Amendment",
                "Tenth Amendment",
                "Eleventh Amendment",
                "Seventeenth Amendment",
                "B", 1, 64, "easy"));


        unit1Questions.add(new Question(
                "The Supremacy Clause primarily ensures that",
                "State constitutions are superior to federal statutes",
                "Federal law prevails when validly enacted and in conflict with state law",
                "Federal courts must follow state precedents",
                "States may veto federal regulations",
                "B", 1, 65, "easy"));


        unit1Questions.add(new Question(
                "A project grant is typically awarded",
                "By a fixed formula such as population",
                "On the basis of competitive applications for a specific purpose",
                "Only during emergencies declared by the president",
                "To private corporations only",
                "B", 1, 66, "medium"));


        unit1Questions.add(new Question(
                "Formula grants distribute money based on",
                "Competitive merit scoring",
                "A pre-established calculation such as income or population",
                "Random assignment by lottery",
                "Requests from individual members of Congress",
                "B", 1, 67, "medium"));


        unit1Questions.add(new Question(
                "Devolution refers to",
                "Transferring responsibilities from the national government to the states",
                "Shifting power from states to cities",
                "Centralizing authority in federal agencies",
                "Expanding judicial review",
                "A", 1, 68, "medium"));


        unit1Questions.add(new Question(
                "Which scenario BEST illustrates preemption?",
                "A state raises its minimum wage above the federal level",
                "Congress passes a law occupying a policy field so conflicting state laws are invalid",
                "A state court reverses a federal conviction",
                "The president issues an executive agreement with a foreign nation",
                "B", 1, 69, "hard"));


        unit1Questions.add(new Question(
                "Which is an example of an implied power?",
                "Declaring war",
                "Establishing a national bank to manage currency and credit",
                "Coining money",
                "Admitting new states",
                "B", 1, 70, "medium"));


        unit1Questions.add(new Question(
                "The Necessary and Proper Clause is located in",
                "Article I, Section 8",
                "Article II, Section 2",
                "Article III, Section 1",
                "Article IV, Section 1",
                "A", 1, 71, "easy"));


        unit1Questions.add(new Question(
                "The concept of limited government in the Constitution is MOST clearly reflected by",
                "Enumerated powers and explicit prohibitions like ex post facto bans",
                "Congress’s power to suspend elections",
                "State authority to coin money",
                "Creation of a national church",
                "A", 1, 72, "easy"));


        unit1Questions.add(new Question(
                "Which constitutional safeguard protects detained individuals by requiring government to justify their detention to a court?",
                "Writ of habeas corpus",
                "Ex post facto",
                "Bill of attainder",
                "Prior restraint",
                "A", 1, 73, "medium"));


        unit1Questions.add(new Question(
                "A central argument of Federalist No. 10 is that a large republic",
                "Eliminates faction by banning interest groups",
                "Prevents corruption by relying on direct democracy",
                "Dilutes the influence of any single faction across many interests",
                "Is unstable and unsustainable over time",
                "C", 1, 74, "easy"));


        unit1Questions.add(new Question(
                "Which of the following BEST characterizes a confederation?",
                "National laws are supreme over states",
                "Sovereign states create a central body with limited powers",
                "Power is divided between national and state governments by a constitution",
                "All power is centralized in a national government",
                "B", 1, 75, "medium"));


        unit1Questions.add(new Question(
                "During ratification debates, The Federalist Papers primarily sought to",
                "Persuade New York to support the Constitution",
                "Replace the Declaration of Independence",
                "Call for secession from the union",
                "Draft a bill of rights",
                "A", 1, 76, "medium"));


        unit1Questions.add(new Question(
                "Which of the following is an example of a state power over elections recognized by the Constitution?",
                "Setting the date of presidential elections",
                "Drawing congressional district boundaries and administering polling",
                "Selecting Supreme Court justices",
                "Counting electoral votes in Congress",
                "B", 1, 77, "medium"));


        unit1Questions.add(new Question(
                "The supremacy of national treaties over state law derives from",
                "Article II’s Take Care Clause",
                "Article VI’s Supremacy Clause",
                "The Tenth Amendment",
                "The Preamble",
                "B", 1, 78, "easy"));


        unit1Questions.add(new Question(
                "Which principle is MOST consistent with the phrase 'consent of the governed'?",
                "Popular sovereignty",
                "Judicial supremacy",
                "Divided government",
                "Executive privilege",
                "A", 1, 79, "easy"));


        unit1Questions.add(new Question(
                "Which statement about the amendment process is accurate?",
                "All amendments have been ratified by state conventions",
                "Proposals require a simple majority of both houses of Congress",
                "Most amendments are proposed by two-thirds of Congress and ratified by three-fourths of state legislatures",
                "The president can veto proposed amendments",
                "C", 1, 80, "medium"));


        unit1Questions.add(new Question(
                "Which scenario best demonstrates the Privileges and Immunities Clause?",
                "A state charges out-of-state citizens double sales tax",
                "A state allows only its residents to vote in state elections",
                "A state lets nonresidents practice law after meeting the same qualifications as residents",
                "A state refuses to honor divorces granted in other states",
                "C", 1, 81, "hard"));


        unit1Questions.add(new Question(
                "A key reason for holding a constitutional convention in 1787 was",
                "To design a monarchy",
                "To address the national government’s inability to respond to internal disorder like Shays’ Rebellion",
                "To abolish all state governments",
                "To establish a national church",
                "B", 1, 82, "easy"));


        unit1Questions.add(new Question(
                "Which pair is CORRECT?",
                "Full Faith and Credit—prohibits unreasonable searches",
                "Supremacy—federal law over state law when validly enacted",
                "Establishment—protects free exercise of religion",
                "Commander in Chief—Article I power",
                "B", 1, 83, "easy"));


        unit1Questions.add(new Question(
                "Which of the following is the BEST example of fiscal federalism?",
                "The president appoints federal judges",
                "Congress provides education funds to states with testing requirements attached",
                "The Supreme Court reviews a state law",
                "A state raises taxes to fund a city project",
                "B", 1, 84, "easy"));


        unit1Questions.add(new Question(
                "The Commerce Clause grants Congress the power to regulate",
                "Trade within a single state exclusively",
                "Trade among the states, with foreign nations, and with Indian tribes",
                "All aspects of criminal law",
                "Local zoning ordinances",
                "B", 1, 85, "medium"));


        unit1Questions.add(new Question(
                "Which was a structural protection for liberty favored by Madison?",
                "Consolidating all powers in Congress",
                "Separation of powers across distinct branches",
                "Selecting judges by popular election",
                "Annual national referenda on laws",
                "B", 1, 86, "easy"));


        unit1Questions.add(new Question(
                "A mandate that states install accessible voting machines without federal funding would be",
                "A block grant",
                "A categorical grant",
                "An unfunded mandate",
                "Revenue sharing",
                "C", 1, 87, "easy"));


        unit1Questions.add(new Question(
                "Which statement about enumerated and reserved powers is TRUE?",
                "Reserved powers belong to the national government",
                "Enumerated powers arise only from the Tenth Amendment",
                "Reserved powers are those not delegated to the United States by the Constitution",
                "Enumerated powers can be created by state constitutions",
                "C", 1, 88, "medium"));


        unit1Questions.add(new Question(
                "The 'necessary and proper' language was controversial because opponents feared it would",
                "Prohibit states from taxing residents",
                "Enable unlimited expansion of federal authority",
                "Eliminate the Senate",
                "Create judicial review",
                "B", 1, 89, "medium"));


        unit1Questions.add(new Question(
                "Which principle is illustrated when Congress overrides a presidential veto?",
                "Federalism",
                "Popular sovereignty",
                "Checks and balances",
                "Judicial review",
                "C", 1, 90, "easy"));


        unit1Questions.add(new Question(
                "Which model of democracy is best illustrated by many competing interest groups shaping public policy through bargaining and compromise?",
                "Participatory democracy", "Pluralist democracy", "Elite democracy", "Direct democracy",
                "B", 1, 91, "medium"));




        unit1Questions.add(new Question(
                "Which of the following is TRUE about state sovereignty under the Constitution?",
                "States may coin their own currency",
                "States retain broad police powers over health, safety, and morals",
                "States can declare war if attacked",
                "States can tax federal agencies directly without limit",
                "B", 1, 92, "medium"));


        unit1Questions.add(new Question(
                "Which policy area historically demonstrates significant state control due to reserved powers?",
                "Foreign policy",
                "Postal service",
                "Education",
                "Immigration and naturalization",
                "C", 1, 93, "easy"));


        unit1Questions.add(new Question(
                "Which effect did the New Deal have on federalism?",
                "It collapsed federal programs into state control",
                "It ushered in cooperative federalism with expanded national role through grants-in-aid",
                "It abolished the Necessary and Proper Clause",
                "It eliminated the Supreme Court’s role in federalism disputes",
                "B", 1, 94, "hard"));


        unit1Questions.add(new Question(
                "Which statement best captures the Anti-Federalist critique of a large republic?",
                "It protects minority rights better than small republics",
                "It ensures more civic virtue and accountability in a small community",
                "It makes factions impossible",
                "It guarantees equal economic outcomes",
                "B", 1, 95, "medium"));


        unit1Questions.add(new Question(
                "What problem did the Great Compromise solve?",
                "How to choose Supreme Court justices",
                "How states would be represented in Congress",
                "Whether to include a bill of rights",
                "How to count electoral votes",
                "B", 1, 96, "easy"));


        unit1Questions.add(new Question(
                "Which clause has been used to justify national minimum-wage laws and anti-discrimination rules applied to businesses engaged in interstate trade?",
                "Free Exercise Clause",
                "Commerce Clause",
                "Establishment Clause",
                "Extradition Clause",
                "B", 1, 97, "medium"));


        unit1Questions.add(new Question(
                "Which of the following is most consistent with limited government?",
                "Enumerated powers and constitutional constraints on rulers",
                "Parliamentary supremacy over written constitutions",
                "Unlimited emergency powers for the executive",
                "Nationalization of all private property",
                "A", 1, 98, "easy"));


        unit1Questions.add(new Question(
                "Which founding document most explicitly asserts that people may alter or abolish a government that violates natural rights?",
                "The Constitution of the United States", "The Declaration of Independence",
                "Federalist No. 51", "Brutus No. 1",
                "B", 1, 99, "easy"));




        unit1Questions.add(new Question(
                "Which scenario best shows 'conditions of aid' tied to a categorical grant?",
                "Congress gives states funds to spend on anything they choose",
                "Congress gives transportation funds only if states adopt seat-belt enforcement policies",
                "Congress orders states to reduce emissions without financial help",
                "A state forms a compact with a neighbor without Congress’s knowledge",
                "B", 1, 100, "medium"));


        unit1Questions.add(new Question(
                "In Federalist No. 51, Madison argues that the separation of powers is essential because",
                "It allows the majority to dominate policy without checks",
                "It prevents any one branch from gaining too much power by enabling ambition to counteract ambition",
                "It ensures Congress dominates the federal government",
                "It centralizes authority in the executive branch",
                "B", 1, 101, "hard"));


        unit1Questions.add(new Question(
                "Which constitutional provision best exemplifies the principle of federalism?",
                "The Necessary and Proper Clause", "The Supremacy Clause", "The Tenth Amendment", "The Elastic Clause",
                "C", 1, 102, "hard"));


        unit1Questions.add(new Question(
                "Brutus No. 1 warned that the Necessary and Proper Clause combined with which other clause would create uncontrollable federal authority?",
                "Full Faith and Credit Clause", "Supremacy Clause", "Commerce Clause", "Establishment Clause",
                "B", 1, 103, "hard"));


        unit1Questions.add(new Question(
                "Under the Articles of Confederation, the national government lacked the power to",
                "Regulate interstate commerce and levy taxes directly on citizens", "Declare war", "Negotiate treaties", "Request money from states",
                "A", 1, 104, "hard"));


        unit1Questions.add(new Question(
                "The Connecticut Compromise resolved a dispute between large and small states over",
                "The method of electing the president", "The structure of Congress", "The power of the judiciary", "The Bill of Rights",
                "B", 1, 105, "hard"));


        unit1Questions.add(new Question(
                "Which Supreme Court case expanded Congress’s implied powers through the Necessary and Proper Clause?",
                "Gibbons v. Ogden", "McCulloch v. Maryland", "US v. Lopez", "Wickard v. Filburn",
                "B", 1, 106, "hard"));


        unit1Questions.add(new Question(
                "US v. Lopez (1995) limited Congress's use of which enumerated power?",
                "Taxing and Spending", "War Powers", "Commerce Clause", "Judicial Review",
                "C", 1, 107, "hard"));


        unit1Questions.add(new Question(
                "The main argument in Federalist No. 10 is that",
                "Factions are best controlled by a large republic", "Direct democracy is preferable to a republic",
                "State sovereignty is necessary for liberty", "The president should be directly elected",
                "A", 1, 108, "hard"));


        unit1Questions.add(new Question(
                "Categorical grants differ from block grants in that they",
                "Give states more discretion over spending", "Are specific and come with conditions attached",
                "Are prohibited under the Constitution", "Are used for national defense only",
                "B", 1, 109, "hard"));


        unit1Questions.add(new Question(
                "Shays’ Rebellion was significant because it",
                "Led to the Bill of Rights", "Demonstrated the weaknesses of the Articles of Confederation",
                "Strengthened state power over the federal government", "Created the Electoral College",
                "B", 1, 110, "hard"));


        unit1Questions.add(new Question(
                "The principle of popular sovereignty is best reflected in which phrase?",
                "We the People", "Necessary and Proper", "Full Faith and Credit", "Supremacy Clause",
                "A", 1, 111, "hard"));


        unit1Questions.add(new Question(
                "The Federalists promised to add a Bill of Rights to",
                "Win support from Anti-Federalists for ratification", "Appease the British government",
                "Limit state powers", "Expand presidential powers",
                "A", 1, 112, "hard"));


        unit1Questions.add(new Question(
                "Dual federalism is often compared to",
                "A marble cake", "A layer cake", "A salad bowl", "A pyramid",
                "B", 1, 113, "hard"));


        unit1Questions.add(new Question(
                "Which amendment shifted significant power to the federal government by authorizing the income tax?",
                "16th Amendment", "10th Amendment", "14th Amendment", "17th Amendment",
                "A", 1, 114, "hard"));


        unit1Questions.add(new Question(
                "The Full Faith and Credit Clause primarily requires states to",
                "Accept other states’ public acts, records, and judicial proceedings", "Adopt the same criminal laws",
                "Share tax revenue equally", "Allow citizens to vote in all states",
                "A", 1, 115, "hard"));


        unit1Questions.add(new Question(
                "Which type of democracy emphasizes broad participation in politics by citizens?",
                "Elite democracy", "Pluralist democracy", "Participatory democracy", "Direct monarchy",
                "C", 1, 116, "hard"));


        unit1Questions.add(new Question(
                "Which clause has been most used to expand national government power?",
                "Commerce Clause", "Establishment Clause", "Privileges and Immunities", "Ex Post Facto Clause",
                "A", 1, 117, "hard"));


        unit1Questions.add(new Question(
                "The process of amending the Constitution reflects which principle?",
                "Federalism", "Judicial Review", "Checks and Balances", "Natural Rights",
                "A", 1, 118, "hard"));


        unit1Questions.add(new Question(
                "The Great Compromise established",
                "Equal representation in the Senate and proportional representation in the House",
                "The Three-Fifths Compromise", "The Bill of Rights", "Judicial Review",
                "A", 1, 119, "hard"));


        unit1Questions.add(new Question(
                "The reserved powers of the states are guaranteed by which amendment?",
                "10th", "9th", "14th", "5th",
                "A", 1, 120, "hard"));

        numUnit1Questions = unit1Questions.size();
        System.out.println(String.format("There are %d questions in unit 1", numUnit1Questions));
    }


}

