import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {NaucnaOblastComponent} from './naucna-oblast/naucna-oblast.component';
import {FinishPageComponent} from './finish-page/finish-page.component';
import {LoginComponent} from './login/login.component';
import {EmailComponent} from './email/email.component';
import {AfterEmailComponent} from './after-email/after-email.component';
import {LoginAdminComponent} from './login-admin/login-admin.component';
import {FinishPageRecenzentComponent} from './finish-page-recenzent/finish-page-recenzent.component';
import {PotvrdaAdminComponent} from './potvrda-admin/potvrda-admin.component';
import {RecencentAdminComponent} from './recencent-admin/recencent-admin.component';
import {KreiranjeCasopisaComponent} from './kreiranje-casopisa/kreiranje-casopisa.component';
import {NaucnaOblastCasopisComponent} from './naucna-oblast-casopis/naucna-oblast-casopis.component';
import {UredniciRecenzentiComponent} from './urednici-recenzenti/urednici-recenzenti.component';
import {LoginAdminCasopisComponent} from './login-admin-casopis/login-admin-casopis.component';
import {AktivacijaCasopisAdminComponent} from './aktivacija-casopis-admin/aktivacija-casopis-admin.component';
import {FinishPageCasopisComponent} from './finish-page-casopis/finish-page-casopis.component';
import {AZapocniObraduComponent} from './a-zapocni-obradu/a-zapocni-obradu.component';
import {ALoginObradaComponent} from './a-login-obrada/a-login-obrada.component';
import {AIzborCasopisaComponent} from './a-izbor-casopisa/a-izbor-casopisa.component';
import {AUnosInfoRadComponent} from './a-unos-info-rad/a-unos-info-rad.component';
import {AUnosKoautoraComponent} from './a-unos-koautora/a-unos-koautora.component';
import {APotvrdaNastavakComponent} from './a-potvrda-nastavak/a-potvrda-nastavak.component';
import {APregledRadaGlUrednikComponent} from './a-pregled-rada-gl-urednik/a-pregled-rada-gl-urednik.component';
import {AIzmenaRadaAutorComponent} from './a-izmena-rada-autor/a-izmena-rada-autor.component';
import {ALoginDrugiObradaComponent} from './a-login-drugi-obrada/a-login-drugi-obrada.component';
import {AHomepageComponent} from './a-homepage/a-homepage.component';
import {APregledPdfGlUrednikComponent} from './a-pregled-pdf-gl-urednik/a-pregled-pdf-gl-urednik.component';
import {AKrajTematskiNeprihvatljivComponent} from './a-kraj-tematski-neprihvatljiv/a-kraj-tematski-neprihvatljiv.component';
import {AIzborRecenzenataComponent} from './a-izbor-recenzenata/a-izbor-recenzenata.component';
import {ARecenziranjeUrednikComponent} from './a-recenziranje-urednik/a-recenziranje-urednik.component';
import {APlacanjeClanarineComponent} from './a-placanje-clanarine/a-placanje-clanarine.component';

const routes: Routes = [

  // KT3 - Proces registracije
  { path: 'naucnaOblast/:processInstanceId', component: NaucnaOblastComponent },
  { path: 'finishPage/:processInstanceId', component: FinishPageComponent },
  { path: 'finishPageRecenzent/:processInstanceId', component: FinishPageRecenzentComponent },
  { path: 'login', component: LoginComponent },
  { path: 'loginAdmin/:processInstanceId', component: LoginAdminComponent },
  { path: 'email/:processInstanceId', component: EmailComponent },
  { path: 'afterEmail/:email/:processInstanceId', component: AfterEmailComponent},
  { path: 'novaPotvrdaAdmin/:processInstanceId', component: PotvrdaAdminComponent },
  { path: 'recAdmin/:processInstanceId', component: RecencentAdminComponent },

  // KT3 - Proces kreiranja novog casopisa
  { path: 'kreiranjeCasopisa', component: KreiranjeCasopisaComponent},
  { path: 'naucnaOblastCasopis/:processInstanceId', component: NaucnaOblastCasopisComponent },
  { path: 'uredRec/:processInstanceId', component: UredniciRecenzentiComponent },
  { path: 'loginAdminCasopis/:processInstanceId', component: LoginAdminCasopisComponent },
  { path: 'aktivacijaCasAdmin/:processInstanceId', component: AktivacijaCasopisAdminComponent },
  { path: 'finishPageCasopis', component: FinishPageCasopisComponent },

  // KT 4 = ODBRANA - Proces obrade podnetog teksta
  { path: 'zapocniObradu', component: AZapocniObraduComponent },
  { path: 'loginObrada/:processInstanceId', component: ALoginObradaComponent },
  { path: 'potvrdaNastavak/:processInstanceId', component: APotvrdaNastavakComponent },
  { path: 'izborCasopisa/:processInstanceId', component: AIzborCasopisaComponent },
  { path: 'unosInfoRad/:processInstanceId', component: AUnosInfoRadComponent },
  { path: 'unosKoautora/:processInstanceId', component: AUnosKoautoraComponent },
  { path: 'pregledRadaUrednik/:processInstanceId/:taskId', component: APregledRadaGlUrednikComponent },
  { path: 'izmenaRadaAutor/:processInstanceId/:taskId', component: AIzmenaRadaAutorComponent },
  { path: 'homepage/:processInstanceId', component: AHomepageComponent },
  { path: 'loginDrugiObrada/:processInstanceId', component: ALoginDrugiObradaComponent },
  { path: 'pregledPdfUrednik/:processInstanceId', component: APregledPdfGlUrednikComponent},
  { path: 'krajTematskiNeprihvatljiv', component: AKrajTematskiNeprihvatljivComponent},
  { path: 'izborRecenzenata/:processInstanceId/:taskId', component: AIzborRecenzenataComponent },
  { path: 'recenziranjeGlUrednik/:processInstanceId/:taskId', component: ARecenziranjeUrednikComponent },
  { path: 'placanjeClanarine/:processInstanceId', component: APlacanjeClanarineComponent }










];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
