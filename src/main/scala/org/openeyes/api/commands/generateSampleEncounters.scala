package org.openeyes.api.commands

import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models._
import org.openeyes.api.models.workflow.{FormComponent,Ticket,Workflow}
import org.openeyes.api.services.{EncounterService,PatientService}
import org.openeyes.api.services.workflow.WorkflowService
import scala.collection.Seq
import scala.reflect.runtime.universe._
import scala.reflect.api
import scala.util.Random

object generateSampleEncounters {
  val rand = new Random

  val allergies = Seq(
    "Penicillin",
    "Sulphonamides",
    "Iodine",
    "Cephalosporins",
    "Tetracycline",
    "Preservatives",
    "Atropine",
    "NSAIDs",
    "Acetazolamide",
    "Phenytoin",
    "Carbamezapine",
    "Latex"
  )

  val anaestheticTypes = Seq(
    "Topical",
    "LAC",
    "LA",
    "LAS",
    "GA"
  )

  val anaestheticDeliveries = Seq(
    "Retrobulbar",
    "Peribulbar",
    "Subtenons",
    "Subconjunctival",
    "Topical",
    "Topical and intracameral",
    "Other"
  )

  val anaestheticAgents = Seq(
    "G Amethocaine",
    "G Benoxinate",
    "G Proxymetacaine",
    "Lignocaine 1%",
    "Bupivocaine",
    "Hyalase"
  )

  val anteriorSegmentEyeDraws = Seq(
    """[{"scaleLevel": 1,"version":1.1,"subclass":"NuclearCataract","originX":0,"originY":0,"apexY":-90,"order":0},{"scaleLevel": 1,"version":1.1,"subclass":"AntSeg","pupilSize":"Medium","apexY":-193,"rotation":0,"pxe":true,"coloboma":false,"colour":"Blue","ectropion":false,"order":1},{"scaleLevel": 1,"version":1.1,"subclass":"RK","apexY":-100,"scaleX":1.00,"scaleY":1.00,"rotation":0,"numberOfCuts":"8","order":2}]""",
    """[{"scaleLevel": 1,"version":1.1,"subclass":"PCIOL","originX":0,"originY":0,"rotation":333,"order":0},{"scaleLevel": 1,"version":1.1,"subclass":"AntSeg","pupilSize":"Medium","apexY":-176,"rotation":5,"pxe":false,"coloboma":false,"colour":"Blue","ectropion":true,"order":1}]"""
  )

  val antiseptics = Seq("Iodine 5%", "Chlorhexidine")

  val clinicOutcomeStatuses = Seq("Follow-up", "Discharge")

  val clinicOutcomePeriods = Seq("days", "weeks", "months", "years")

  val clinicOutcomeRoles = Seq(
    "Consultant",
    "Non Consultant Doctor",
    "Optometrist",
    "Clinic Nurse",
    "Other",
    "Any Clinician"
  )

  val comments = Seq(
    "A comment",
    "Another comment",
    "This is a comment"
  )

  val comorbidities = Seq(
    "Angina",
    "Asthma",
    "Blood Loss",
    "Cardiac Surgery",
    "CVA",
    "Family history of Glaucoma",
    "Hyperopia",
    "Hypotension",
    "Myopia",
    "Migraine",
    "Raynaud's",
    "Shortness of breath",
    "Other",
    "Refractive Surgery",
    "Chronic airway disease"
  )

  val complications = Seq(
    "Subconjunctival haemorrhage",
    "Conjunctival damage (e.g. tear)",
    "Corneal abrasion",
    "Lens damage",
    "Retinal damage",
    "Other"
  )

  val corrections = Seq(
    "Unaided",
    "Glasses",
    "Contact lens",
    "Pinhole",
    "Auto-refraction",
    "Formal refraction"
  )

  val diagnoses = Seq(
    "Choroidal retinal neovascularisation",
    "Macular retinal oedema",
    "Central serous chorioretinopathy",
    "Idiopathic polypoidal choroidal vasculopathy",
    "Coats' disease",
    "Scleritis",
    "Capillary haemangioma of retina",
    "Parafoveal telangiectasia",
    "Uveitis",
    "Neovascular glaucoma",
    "Vitreomacular traction syndrome"
  )

  val doctors = Seq(
    "Dr Dre",
    "Dr Pepper",
    "Dr Who",
    "Mr Bill Aylward MA MB BChir FRCS FRCOphth MD"
  )

  val parentDiagnoses = Seq(
    "Age related macular degeneration",
    "Myopia",
    "Punctate inner choroidopathy",
    "Ocular histoplasmosis syndrome",
    "Idiopathic choroidal neovascular membrane",
    "Angioid streaks of choroid",
    "Naevus of choroid",
    "Osteoma of choroid",
    "Choroidal rupture",
    "Venous retinal branch occlusion",
    "Central retinal vein occlusion",
    "Hereditary macular dystrophy",
    "Sarcoidosis",
    "Multifocal choroiditis",
    "Toxoplasma retinitis",
    "Uveitis related cystoid macular oedema",
    "Central serous chorioretinopathy",
    "Diabetic macular oedema",
    "Cystoid macular oedema",
    "Hereditary macular dystrophy"
  )

  val dilationDrugs = Seq(
    "Atropine 1%",
    "Cyclopentolate 0.5%",
    "Cyclopentolate 1%",
    "Phenylephrine 2.5%",
    "Tropicamide 0.5%",
    "Tropicamide 1%"
  )

  val iopLowerers = Seq(
    "Iopidine 0.5%",
    "Iopidine 1.0%",
    "Diamox 250mg",
    "Diamox 500mg"
  )

  val injectionDrugs = Seq(
    "Bevacizumab",
    "Aflibercept",
    "Photodynamic therapy",
    "Ranibizumab",
    "Ozurdex",
    "Fluocinolone acetonide (Iluvien)",
    "Ocriplasmin",
    "Vodka"
  )

  val injectionRisks = Seq(
    "Pre-existing glaucoma",
    "Previous glaucoma surgery (trabeculectomy bleb, glaucoma draining device)",
    "Allergy to povidone iodine",
    "Previous interocular surgery",
    "CVA",
    "MI",
    "Pregnancy"
  )

  val injectionManagementQuestions = Seq(
    "CRT Increase  <100",
    "CRT >= 100",
    "Loss of 5 letters",
    "Loss of letter  >5",
    "Failed Laser",
    "Unsuitable for Laser",
    "Previously received intravitreal treatment",
    "CRT >= 400 µ",
    "Foveal structural damage",
    "Failed laser",
    "Unsuitable for Laser",
    "Previously received Anti VEGF",
    "Epiretinal membrane present",
    "Is it a stage II full thickness macular hole with diameter <=400 micrometers",
    "Do they have severe symptoms"
  )

  val injectionSiteEyeDraws = Seq(
    """[{"scaleLevel": 0.5,"version":1.1,"subclass":"AntSeg","pupilSize":"Medium","apexY":-160,"rotation":0,"pxe":true,"coloboma":false,"colour":"Brown","ectropion":false,"order":0},{"scaleLevel": 0.5,"version":1.1,"subclass":"InjectionSite","apexY":-470,"rotation":289,"order":1}]""",
    """[{"scaleLevel": 0.5,"version":1.1,"subclass":"AntSeg","pupilSize":"Medium","apexY":-166,"rotation":0,"pxe":false,"coloboma":false,"colour":"Brown","ectropion":true,"order":0},{"scaleLevel": 0.5,"version":1.1,"subclass":"InjectionSite","apexY":-470,"rotation":70,"order":1}]"""
  )

  val lensStatuses = Seq("Phakic", "Aphakic", "Pseudo-phakic")

  val posteriorPoleEyeDraws = Seq(
    """[{"scaleLevel": 1,"version":1.1,"subclass":"PostPole","apexX":300,"apexY":-40,"order":0},{"scaleLevel": 1,"version":1.1,"subclass":"FocalLaser","originX":-150,"originY":-150,"apexY":-75,"order":1},{"scaleLevel": 1,"version":1.1,"subclass":"Geographic","apexX":0,"apexY":-100,"scaleX":0.56,"scaleY":0.56,"order":2},{"scaleLevel": 1,"version":1.1,"subclass":"EpiretinalMembrane","originX":0,"originY":0,"scaleX":1.00,"scaleY":1.00,"rotation":0,"order":3}]""",
    """[{"scaleLevel": 1,"version":1.1,"subclass":"PostPole","apexX":-300,"apexY":-40,"order":0},{"scaleLevel": 1,"version":1.1,"subclass":"IRMA","originX":-3,"originY":104,"scaleX":1.00,"scaleY":1.00,"rotation":0,"order":1},{"scaleLevel": 1,"version":1.1,"subclass":"CNV","originX":100,"originY":-103,"scaleX":0.50,"scaleY":0.50,"order":2},{"scaleLevel": 1,"version":1.1,"subclass":"CNV","originX":-87,"originY":-100,"scaleX":0.57,"scaleY":0.57,"order":3}]"""
  )

  val postInjectionDrops = Seq(
    "G. Levofloxacin four times daily for 5 days",
    "G. Chloramphenicol 0.5%  four times daily for 5 days",
    "None",
    "G. Chloramphenicol 0.5% once only"
  )

  def apply = {
    Ticket.findAll.filter(_.stepIndex > 0).foreach (t => {
      WorkflowService.find(t.workflowId.toString) map (wf => (
        generateBigEncounter(t.patient, wf, Some(t))
      ))
    })
    val allWorkflows = WorkflowService.findAll
    PatientService.findAll.foreach (p => (
      generateBigEncounter(p, allWorkflows(rand.nextInt(allWorkflows.length)))
    ))
  }

  def generateBigEncounter(patient: Patient, workflow: Workflow, ticket: Option[Ticket] = None) = {
    for (step <- 0 until (ticket match {case Some(t) => t.stepIndex case None => workflow.steps.length})) {
      EncounterService.create(EncounterForm(
        patient._id.toString,
        for (component <- workflow.steps(step).components if (component.isInstanceOf[FormComponent])) yield generateElement(component.name),
        ticket map (_._id.toString),
        Some(step)))
    }
  }

  def generateElement(name: String) = {
    def randVal[T](seq: Seq[T]) = seq(rand.nextInt(seq.length))

    def randOption[T](v: T) = if (rand.nextBoolean) Some(v) else None

    def randOptionVal[T](seq: Seq[T]) = randOption(randVal(seq))

    def randList[T](fun: () => T, bound: Int = 4) = (0 to rand.nextInt(bound) map (_ => fun.apply) toList)

    def randListVal[T](seq: Seq[T]) = randList(() => randVal(seq))

    def randDate() = f"${rand.nextInt(20) + 2000}%04d-${rand.nextInt(12) + 1}%02d-${rand.nextInt(28) + 1}%02d"

    def randTime() = f"${rand.nextInt(24)}%02d:${rand.nextInt(60)}%02d"

    def randPair[T](fun: () => T) = rand.nextInt(3) match {
      case 0 => (None, Some(fun.apply))
      case 1 => (Some(fun.apply), None)
      case 2 => (Some(fun.apply), Some(fun.apply))
    }

    name match {
      case "Allergies" => Allergies(randList(() => Allergy(randVal(allergies), randOptionVal(comments))))
      case "Anaesthetic" => Anaesthetic.tupled(randPair(() => AnaestheticSide(randVal(anaestheticTypes), randVal(anaestheticDeliveries), randVal(anaestheticAgents))))
      case "AnteriorSegment" => AnteriorSegment.tupled(randPair(() => AnteriorSegmentSide(randVal(anteriorSegmentEyeDraws), randOptionVal(comments))))
      case "ClinicalManagement" => ClinicalManagement(randVal(comments))
      case "ClinicOutcome" => ClinicOutcome(randVal(clinicOutcomeStatuses), rand.nextInt(12) + 1, randVal(clinicOutcomePeriods), rand.nextBoolean, randVal(clinicOutcomeRoles))
      case "Comorbidities" => Comorbidities(randListVal(comorbidities))
      case "Complications" => Complications(randOption(ComplicationsSide(randListVal(complications))), randOption(ComplicationsSide(randListVal(complications))))
      case "Dilation" => Dilation.tupled(randPair(() => DilationSide(randList(() => DilationRecord(randTime, randVal(dilationDrugs), rand.nextInt(5))))))
      case "History" => History(randVal(comments))
      case "InjectionSite" => InjectionSite.tupled(randPair(() => InjectionSiteSide(randVal(injectionSiteEyeDraws), randVal(lensStatuses))))
      case "InjectionManagement" => InjectionManagement.tupled(randPair(() =>
        InjectionManagementSide(
          rand.nextBoolean, randVal(diagnoses), randVal(parentDiagnoses),
          randList(() => randVal(injectionManagementQuestions) + "? " + (if(rand.nextBoolean) "Yes" else "No")),
          randVal(injectionDrugs), randListVal(injectionRisks), randOptionVal(comments)
        ))
      )
      case "PosteriorPole" => PosteriorPole.tupled(randPair(() => PosteriorPoleSide(randVal(posteriorPoleEyeDraws), randOptionVal(comments))))
      case "PostInjectionExam" => PostInjectionExam.tupled(randPair(() => PostInjectionExamSide(rand.nextBoolean, rand.nextBoolean, randVal(postInjectionDrops))))
      case "Treatment" => Treatment.tupled(randPair(() => TreatmentSide(rand.nextInt(9000).toString, randDate, randVal(doctors))))
      case "TreatmentOrder" => TreatmentOrder.tupled(randPair(() => TreatmentOrderSide(randVal(antiseptics), randVal(antiseptics), randListVal(iopLowerers), randVal(injectionDrugs), rand.nextInt(100), randTime, randListVal(iopLowerers))))
      case "VisualAcuity" => VisualAcuity.tupled(randPair(() => VisualAcuitySide(randList(() => VisualAcuityReading(rand.nextInt(120), randVal(corrections))), randOptionVal(comments))))
    }
  }

  // Move along, nothing to see here
  def generateModel(className: String) :Any = {
    val mirror = runtimeMirror(Class.forName(className).getClassLoader)
    val classSymbol = mirror.staticClass(className)
    val ctor = classSymbol.selfType.declaration(nme.CONSTRUCTOR).asMethod

    mirror.reflectClass(classSymbol).reflectConstructor(ctor).apply(
      ctor.paramLists.flatten.map (param => {
        val clarse = mirror.runtimeClass(param.typeSignature.typeSymbol.asClass)

        // I can't get this to work with pattern matching, I can only assume Martin Odersky hates me
        if (clarse == classOf[Boolean]) {
          false
        } else if (clarse == classOf[Int]) {
          42
        } else if (clarse == classOf[String]) {
          "Foo"
        } else {
          generateModel(clarse.toString)
        }
      }): _*
    )
  }
}
