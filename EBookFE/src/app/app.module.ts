import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { NaucnaOblastComponent } from './naucna-oblast/naucna-oblast.component';
import {AppRoutingModule} from './app-routing-module';
import {RepositoryComponent} from './repository/repository.component';
import {UsersComponent} from './users/users.component';
import {NaucnaOblastService} from './services/naucna-oblast/naucna-oblast.service';
import { FinishPageComponent } from './finish-page/finish-page.component';
import { LoginComponent } from './login/login.component';
import { EmailComponent } from './email/email.component';
import { AfterEmailComponent } from './after-email/after-email.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { PotvrdaAdminComponent } from './potvrda-admin/potvrda-admin.component';
import { FinishPageRecenzentComponent } from './finish-page-recenzent/finish-page-recenzent.component';
import { RecencentAdminComponent } from './recencent-admin/recencent-admin.component';
import { KreiranjeCasopisaComponent } from './kreiranje-casopisa/kreiranje-casopisa.component';
import { NaucnaOblastCasopisComponent } from './naucna-oblast-casopis/naucna-oblast-casopis.component';
import { UredniciRecenzentiComponent } from './urednici-recenzenti/urednici-recenzenti.component';
import { LoginAdminCasopisComponent } from './login-admin-casopis/login-admin-casopis.component';
import { AktivacijaCasopisAdminComponent } from './aktivacija-casopis-admin/aktivacija-casopis-admin.component';
import { FinishPageCasopisComponent } from './finish-page-casopis/finish-page-casopis.component';
import { AZapocniObraduComponent } from './a-zapocni-obradu/a-zapocni-obradu.component';
import {ObradaService} from './services/obrada/obrada.service';
import { ALoginObradaComponent } from './a-login-obrada/a-login-obrada.component';
import { AIzborCasopisaComponent } from './a-izbor-casopisa/a-izbor-casopisa.component';
import { AUnosInfoRadComponent } from './a-unos-info-rad/a-unos-info-rad.component';
import { KrajLoginComponent } from './kraj-login/kraj-login.component';
import {AuthService} from './services/auth/auth.service';
import {AuthInterceptor} from './auth-interceptor/auth-interceptor';
import { AUnosKoautoraComponent } from './a-unos-koautora/a-unos-koautora.component';
import { APotvrdaNastavakComponent } from './a-potvrda-nastavak/a-potvrda-nastavak.component';
import { APregledRadaGlUrednikComponent } from './a-pregled-rada-gl-urednik/a-pregled-rada-gl-urednik.component';
import { AIzmenaRadaAutorComponent } from './a-izmena-rada-autor/a-izmena-rada-autor.component';
import { ALoginDrugiObradaComponent } from './a-login-drugi-obrada/a-login-drugi-obrada.component';
import { AHomepageComponent } from './a-homepage/a-homepage.component';
import { APregledPdfGlUrednikComponent } from './a-pregled-pdf-gl-urednik/a-pregled-pdf-gl-urednik.component';
import { AKrajTematskiNeprihvatljivComponent } from './a-kraj-tematski-neprihvatljiv/a-kraj-tematski-neprihvatljiv.component';
import { AIzborRecenzenataComponent } from './a-izbor-recenzenata/a-izbor-recenzenata.component';
import { ARecenziranjeUrednikComponent } from './a-recenziranje-urednik/a-recenziranje-urednik.component';
import { APlacanjeClanarineComponent } from './a-placanje-clanarine/a-placanje-clanarine.component';


const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  }
]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    NaucnaOblastComponent,
    RepositoryComponent,
    UsersComponent,
    NaucnaOblastComponent,
    FinishPageComponent,
    LoginComponent,
    EmailComponent,
    AfterEmailComponent,
    LoginAdminComponent,
    PotvrdaAdminComponent,
    FinishPageRecenzentComponent,
    RecencentAdminComponent,
    KreiranjeCasopisaComponent,
    NaucnaOblastCasopisComponent,
    UredniciRecenzentiComponent,
    LoginAdminCasopisComponent,
    AktivacijaCasopisAdminComponent,
    FinishPageCasopisComponent,
    AZapocniObraduComponent,
    ALoginObradaComponent,
    AIzborCasopisaComponent,
    AUnosInfoRadComponent,
    KrajLoginComponent,
    AUnosKoautoraComponent,
    APotvrdaNastavakComponent,
    APregledRadaGlUrednikComponent,
    AIzmenaRadaAutorComponent,
    ALoginDrugiObradaComponent,
    AHomepageComponent,
    APregledPdfGlUrednikComponent,
    AKrajTematskiNeprihvatljivComponent,
    AIzborRecenzenataComponent,
    ARecenziranjeUrednikComponent,
    APlacanjeClanarineComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule,
    AppRoutingModule,
    HttpModule,
    ReactiveFormsModule
  ],
  
  providers:  [
    Admin,
    Authorized,
    Notauthorized,
    NaucnaOblastService,
    ObradaService,
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
